package com.investdata.common.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.investdata.common.Const;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TGendataSheetDao;
import com.investdata.dao.TStockDao;
import com.investdata.dao.po.GendataSheet;
import com.investdata.dao.po.Stock;
import com.investdata.exception.InitException;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.investdata.utils.StringUtils;

/**
 * 监听器，完成所有财务数据与股票数据的初始化加载
 * @author Administrator
 *
 */
public class WebAppListener implements ServletContextListener {
	Logger _log = Logger.getLogger(WebAppListener.class);
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
//		ServletContext app = event.getServletContext(); //application 对象
		
		try {
			initStockData(); //初始化页面检索数据
			initGendataSheet(); //初始化综合数据表项数据.
		} catch (Exception e) {
			_log.error("监听器初始化数据错误",new InitException());
		}
		
		
	}
	
	/**
	 * 初始化股票检索列表与对应关系数据
	 * @throws Exception
	 */
	private void initStockData() throws Exception{
		_log.info("############监听器step1-begin:准备初始化股票检索与对应关系数据########");
		StringBuilder stocksItems = new StringBuilder();;
		TStockDao stockDao = DaoFactory.getTStockDao();
		Stock stockParam = new Stock();
		Map<String,String> stockCodeMapping = new HashMap<String,String>(); //股票代码与股票名称的对应关系
		stockParam.setFlag(1);
		List<Stock> stocks = stockDao.getStocks(stockParam);
		if (stocks != null && stocks.size() > 0) {
			_log.info(String.format("股票列表数据加载完毕stocks.size=[%s]", stocks.size()));
			StringBuilder stockSelKey = new StringBuilder();
			
			stocksItems.append("[");
			for (int i=0; i< stocks.size(); i++) {
				Stock stock = stocks.get(i);
				String code = stock.getCode();
				String name = stock.getName();
				String shrotName = stock.getShortName();
				String market = stock.getMarket();
				
				stockCodeMapping.put(code, name);
				
				/**
				 * 最终格式
				 * [
	                  {label: "key1", value: "v1" },
	                  {label: "key2", value: "v2" },
	               ];
				 */
				
				stockSelKey
						  .append("{label: \"")
						  .append(StringUtils.fillRSpace(shrotName, 13))
						  .append(StringUtils.fillRSpace(code, 13))
						  .append(StringUtils.fillRSpace(name, 13))
						  .append(market)
						  .append("\",")
						  .append(" value: \"")
						  .append(code)
						  .append("\"")
						  .append(" },")
						  .append("\n");
				
				stocksItems.append(stockSelKey);
				stockSelKey.setLength(0); //清空
				
			}
			stocksItems.deleteCharAt(stocksItems.length() -2 ); //删除最后一个逗号
			stocksItems.append(" ]");
		}else {
			_log.info(String.format("未查询到股票数据"));
		}
		
		Jedis jedis = RedisCache.getJedis();
		jedis.set(Const.STOCK_SEARCH_LIST, stocksItems.toString()); //搜索框检索数据源
		jedis.hmset(Const.STOCK_CODE_MAPPING, stockCodeMapping); //股票代码与名称的对应关系map
		
		_log.info("############监听器step1-end:初始化股票检索与对应关系数据完成########");
	}
	
	
	/**
	 * 初始化综合数据项表数据
	 * 最终的缓冲数据格式为  股票代码--->list<GendataSheet>
	 */
	private void initGendataSheet() throws Exception {
		_log.info("############监听器step2-begin:准备初始化综合数据项表数据########");
		TGendataSheetDao genSheetDao = DaoFactory.getTGendataSheetDao();
		GendataSheet genSheet = new GendataSheet();
		List<GendataSheet> genDataList = genSheetDao.getGendataSheet(genSheet);
		
		Map<String,List<GendataSheet>> gendataSheetListMap = new HashMap<String,List<GendataSheet>>();
		
		//建立股票代码与该股票各年份数据对象之间的对应关系。
		
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
		
		Jedis jedis = RedisCache.getJedis();
		Set<String> keys = gendataSheetListMap.keySet();
		
		for (String key : keys) {
			String compxKey = key + "#" + Const.GENDATAKEY;
			jedis.set(compxKey.getBytes(), ObjectsTranscoder.serialize(gendataSheetListMap.get(key)));
		}
		
		_log.info("############监听器step2-end:初始化综合数据项表数据结束########");
	}
	
}
