package com.investdata.common.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.investdata.common.Const;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TCashFlowSheetDao;
import com.investdata.dao.TFinanceIndexInfoDao;
import com.investdata.dao.TGendataSheetDao;
import com.investdata.dao.TIncstateSheetDao;
import com.investdata.dao.TStockDao;
import com.investdata.dao.po.CashFlowSheet;
import com.investdata.dao.po.FinanceIndexInfo;
import com.investdata.dao.po.GendataSheet;
import com.investdata.dao.po.IncstateSheet;
import com.investdata.dao.po.Stock;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.investdata.utils.StringUtils;

/**
 * 监听器，完成所有财务数据与股票数据的初始化加载
 * 
 * @author Administrator
 * 
 */
public class WebAppListener implements ServletContextListener {
	Logger _log = Logger.getLogger(WebAppListener.class);
	Jedis jedis = RedisCache.getJedis();

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 将所有数据加载到缓存redis中
	 * 后续所有的计算直接从redis取值，增加展示速度。
	 * 
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// ServletContext app = event.getServletContext(); //application 对象
		_log.info("监听器webApplistener 准备初始化数据到redis...");

		try {
			initStockData(); // 初始化页面检索数据
			initFinanceIndexInfo(); // 初始化财务指标信息
			initGendataSheet();  // 初始化综合数据表项数据.
			initIncstateSheet(); //初始化利润表数数据
			initCashFlowSheet(); //初始化现金流量表数据
			initBalanceSheet();  //初始化资产负债表数据
		} catch (Exception e) {
			_log.error("监听器初始化数据错误:" + e.getMessage(), e);
		}

	}

	/**
	 * 初始化股票检索列表与对应关系数据
	 * 
	 * @throws Exception
	 */
	private void initStockData() throws Exception {
		_log.info("############监听器step1-begin:准备初始化股票检索与对应关系数据########");
		StringBuilder stocksItems = new StringBuilder();
		TStockDao stockDao = DaoFactory.getTStockDao();
		Stock stockParam = new Stock();
		Map<String, String> stockCodeMapping = new HashMap<String, String>(); // 股票代码与股票名称的对应关系
		stockParam.setFlag(1);
		List<Stock> stocks = stockDao.getStocks(stockParam);
		if (stocks != null && stocks.size() > 0) {
			_log.info(String.format("股票列表数据加载完毕stocks.size=[%s]", stocks.size()));
			StringBuilder stockSelKey = new StringBuilder();

			stocksItems.append("[");
			for (int i = 0; i < stocks.size(); i++) {
				Stock stock = stocks.get(i);
				String code = stock.getCode();
				String name = stock.getName();
				String shrotName = stock.getShortName();
				String market = stock.getMarket();

				stockCodeMapping.put(code, name);
				/**
				 * 最终格式 [ {label: "key1", value: "v1" }, {label: "key2", value:
				 * "v2" }, ];
				 */
				stockSelKey.append("{label: \"").append(StringUtils.fillRSpace(shrotName, 13)).append(StringUtils.fillRSpace(code, 13)).append(StringUtils.fillRSpace(name, 13)).append(market).append("\",").append(" value: \"").append(code).append("\"").append(" },").append("\n");

				stocksItems.append(stockSelKey);
				stockSelKey.setLength(0); // 清空

			}
			stocksItems.deleteCharAt(stocksItems.length() - 2); // 删除最后一个逗号
			stocksItems.append(" ]");
		} else {
			_log.info(String.format("未查询到股票数据"));
		}
		jedis.set(Const.STOCK_SEARCH_LIST, stocksItems.toString()); // 搜索框检索数据源
		jedis.hmset(Const.STOCK_CODE_MAPPING, stockCodeMapping); // 股票代码与名称的对应关系map

		_log.info("############监听器step1-end:初始化股票检索与对应关系数据完成########");
	}

	/**
	 * 初始化财务指标信息
	 * 
	 * @throws Exception
	 */
	public void initFinanceIndexInfo() throws Exception {
		_log.info("############监听器step2-start:准备初始化财务指标数据########");
		TFinanceIndexInfoDao financeIndexInfoDao = DaoFactory.getTFinanceIndexInfoDao();
		// 获取父级财务指标
		FinanceIndexInfo finIndexInfo = new FinanceIndexInfo();
		finIndexInfo.setPid(0); // 获取根节点的所有子节点
		finIndexInfo.setFlag(1);
		List<FinanceIndexInfo> parentsIndexList = financeIndexInfoDao.getFinanceIndexInfos(finIndexInfo);
		
		if (parentsIndexList != null && parentsIndexList.size() > 0) {
			for (FinanceIndexInfo indexInfo : parentsIndexList) {
				int pid = indexInfo.getId();
				FinanceIndexInfo subFinIndexInfos = new FinanceIndexInfo();
				subFinIndexInfos.setPid(pid);
				subFinIndexInfos.setFlag(1);
				// 获取子类指标
				List<FinanceIndexInfo> childsFinanceIndexInfoList = financeIndexInfoDao.getFinanceIndexInfos(subFinIndexInfos);
				indexInfo.setChildsFinanceIndexInfoList(childsFinanceIndexInfoList);
			}			
			jedis.set(Const.FINANCEINDEXINFO_KEY.getBytes(), ObjectsTranscoder.serialize(parentsIndexList));
		}
		

		_log.info("############监听器step2-end:初始化财务指标数据结束########");
	}

	/**
	 * 初始化综合数据项表数据 最终的缓冲数据格式为 股票代码--->list<GendataSheet>
	 */
	private void initGendataSheet() throws Exception {
		_log.info("############监听器step3-begin:准备初始化综合数据项表数据########");
		TGendataSheetDao genSheetDao = DaoFactory.getTGendataSheetDao();
		GendataSheet genSheet = new GendataSheet();
		List<GendataSheet> genDataList = genSheetDao.getGendataSheets(genSheet);
		
		if (genDataList != null && genDataList.size() > 0) {
			Map<String, List<GendataSheet>> gendataSheetListMap = new HashMap<String, List<GendataSheet>>();
			// 建立股票代码与该股票各年份数据对象之间的对应关系。
			for (GendataSheet genData : genDataList) {
				String code = genData.getCode();
				if (gendataSheetListMap.containsKey(code)) {
					gendataSheetListMap.get(code).add(genData);
				} else {
					List<GendataSheet> gendataList = new ArrayList<GendataSheet>();
					gendataList.add(genData);
					gendataSheetListMap.put(code, gendataList);
				}
			}
			
			Set<String> keys = gendataSheetListMap.keySet();
			for (String key : keys) {
				String compxKey = key + "#" + Const.GENDATA_KEY;
				jedis.set(compxKey.getBytes(), ObjectsTranscoder.serialize(gendataSheetListMap.get(key)));
			}			
		}
		

		_log.info("############监听器step3-end:初始化综合数据项表数据结束########");
	}

	// 初始化利润表数据
	public void initIncstateSheet() throws Exception {
		_log.info("############监听器step4-start:准备初始化利润表数据########");
		
		TIncstateSheetDao isd = DaoFactory.getTIncstateSheetDao();
		IncstateSheet is = new IncstateSheet();
		List<IncstateSheet> inSheetList = isd.getIncstateSheets(is);
		
		if (inSheetList != null && inSheetList.size() > 0) {
			Map<String, List<IncstateSheet>> incstateSheetListMap = new HashMap<String, List<IncstateSheet>>();
			
			// 建立股票代码与该股票各年份数据对象之间的对应关系。
			for (IncstateSheet isSheet : inSheetList) {
				String code = isSheet.getCode();
				if (incstateSheetListMap.containsKey(code)) {
					incstateSheetListMap.get(code).add(isSheet);
				} else {
					List<IncstateSheet> isList = new ArrayList<IncstateSheet>();
					isList.add(isSheet);
					incstateSheetListMap.put(code, isList);
				}
			}
			
			Set<String> keys = incstateSheetListMap.keySet();
			for (String key : keys) {
				String compxKey = key + "#" + Const.INCSTATEDATA_KEY;
				jedis.set(compxKey.getBytes(), ObjectsTranscoder.serialize(incstateSheetListMap.get(key)));
			}			
		}
		
		_log.info("############监听器step4-end:初始化利润表数据结束########");

	}
	
	//初始化现金流量表数据
	public void initCashFlowSheet() throws Exception {
		_log.info("############监听器step5-start:准备初始化现金流量表数据########");
		TCashFlowSheetDao tcs  = DaoFactory.getTCashFlowSheetDao();
		CashFlowSheet cfs = new CashFlowSheet();
		List<CashFlowSheet> cashFlowList = tcs.getCashFlowSheets(cfs);
		
		if (cashFlowList != null && cashFlowList.size() > 1) {
			Map<String, List<CashFlowSheet>> cashFlowSheetListMap = new HashMap<String, List<CashFlowSheet>>();
			// 建立股票代码与该股票各年份数据对象之间的对应关系。
			for (CashFlowSheet cf : cashFlowList) {
				String code = cf.getCode();
				if (cashFlowSheetListMap.containsKey(code)) {
					cashFlowSheetListMap.get(code).add(cf);
				} else {
					List<CashFlowSheet> cfList = new ArrayList<CashFlowSheet>();
					cfList.add(cf);
					cashFlowSheetListMap.put(code, cfList);
				}
			}
			
			Set<String> keys = cashFlowSheetListMap.keySet();
			for (String key : keys) {
				String compxKey = key + "#" + Const.CASHFLOWDATA_KEY;
				jedis.set(compxKey.getBytes(), ObjectsTranscoder.serialize(cashFlowSheetListMap.get(key)));
			}			
		}
		
		_log.info("############监听器step5-end:初始化现金流量表数据结束##########");
	}
	
	//初始化资产负债表数据
	public void initBalanceSheet() throws Exception {
		_log.info("############监听器step6-start:准备初始化资产负债表数据########");
		
		
		_log.info("############监听器step6-end:初始化资产负债表数据结束##########");

	}

}
