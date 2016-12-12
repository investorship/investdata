package com.investdata.action.solvent;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;

import redis.clients.jedis.Jedis;

import com.investdata.common.BaseAction;
import com.investdata.common.Const;
import com.investdata.dao.po.BalanceSheet;
import com.investdata.dao.po.CashFlowSheet;
import com.investdata.dao.po.Chart;
import com.investdata.dao.po.GendataSheet;
import com.investdata.dao.po.IncstateSheet;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.investdata.utils.MathUtils;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 偿债能力各指标计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */
public class SolventAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(SolventAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	private static Jedis jedis = RedisCache.getJedis();
	
	//流动比率
	public String currt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(in);  
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//期末流动资产
				double  liquidAssetsEnd = Double.valueOf(balanceSheet.getLiquidAssetsEnd());
				//流动负债
				double curr_liab = Double.valueOf(balanceSheet.getCurrLiab());
				
				if (curr_liab == 0) continue;
				
				//流动比率= 流动资产 / 流动负债
				String currt = MathUtils.format2DecPoint(liquidAssetsEnd / curr_liab);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(currt).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("流动比率");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//速动比率
	public String qckrt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(in);  
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//期末流动资产
				double  liquidAssetsEnd = Double.valueOf(balanceSheet.getLiquidAssetsEnd());
				//期末存货
				double goodEnd = Double.valueOf(balanceSheet.getGoodsEnd());
				//流动负债
				double curr_liab = Double.valueOf(balanceSheet.getCurrLiab());
				
				if (curr_liab == 0)  continue;
				
				//速动比例 = (流动资产 - 存货 )/ 流动负债
				String qckrt = MathUtils.format2DecPoint((liquidAssetsEnd -goodEnd) / curr_liab);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(qckrt).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("速动比率");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//现金比率
	public String cash() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(in);  
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//货币资金
				double  cash = Double.valueOf(balanceSheet.getCash());
				//交易性金融资产
				double tradeAssets = Double.valueOf(balanceSheet.getTradAssets());
				
				//流动负债
				double curr_liab = Double.valueOf(balanceSheet.getCurrLiab());
				
				if (curr_liab == 0) continue;
				
				//现金比率= (现金等价物+有价证券)/流动负债
				String cashRate = MathUtils.format2DecPoint((cash + tradeAssets) / curr_liab * 100);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(cashRate).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("现金比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//现金流量比率
	public String cashFlow() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度 经营活动产生的现金流量净额
		String incCompxKey = code + "#" + Const.CASHFLOWDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<CashFlowSheet> cashFlowSheetsList =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (balanceSheetsList != null && cashFlowSheetsList !=null && balanceSheetsList.size() > 0 && balanceSheetsList.size() == cashFlowSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				CashFlowSheet cashFlowSheet = cashFlowSheetsList.get(i);
				
				//经营活动产生的现金流量净额.
				double operaActiveCash = Double.valueOf(cashFlowSheet.getOperaActiveCash());
				
				//流动负债
				double currLiab = Double.valueOf(balanceSheet.getCurrLiab());
				
				if (currLiab == 0) continue;
				
				//现金流量比率 = 经营活动产生的现金流量净额/流动负债
				String cashFlow = MathUtils.format2DecPoint(operaActiveCash/ currLiab * 100);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(cashFlow).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("现金流量比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//产权比率
	public String dbequrt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(in);  
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				//期末负债总额
				double totalLiab = Double.valueOf(balanceSheet.getTotalLiabEnd());
				
				//期末股东权益 
				double shareHolderEnd = Double.valueOf(balanceSheet.getShareHolderEnd());
				
				if (shareHolderEnd == 0) continue;
				
				//产权比率 = 负债总额/股东权益	
				String dbequrt = MathUtils.format2DecPoint(totalLiab/ shareHolderEnd * 100);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(dbequrt).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("产权比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//偿债保障比率
	public String debtEnsure() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度 经营活动产生的现金流量净额
		String incCompxKey = code + "#" + Const.CASHFLOWDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<CashFlowSheet> cashFlowSheetsList =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (balanceSheetsList != null && cashFlowSheetsList != null && balanceSheetsList.size() > 0 && balanceSheetsList.size() == cashFlowSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				CashFlowSheet cashFlowSheet = cashFlowSheetsList.get(i);
				
				//期末负债总额
				double totalLiab = Double.valueOf(balanceSheet.getTotalLiabEnd());
				
				//经营活动产生的现金流量净额
				double operaActiveCash = Double.valueOf(cashFlowSheet.getOperaActiveCash());
				
				if (operaActiveCash == 0) continue;
				
				//偿债保障比率 = 负债总额 / 经营活动产生的现金流量净额
				String dbequrt = MathUtils.format2DecPoint(totalLiab / operaActiveCash);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(dbequrt).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("偿债保障比率");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//息税前利润(EBIT)
	public String EBIT() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (incstateSheetList != null && incstateSheetList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i< incstateSheetList.size(); i++) {
				IncstateSheet incstateSheet = incstateSheetList.get(i);
				
				//利润总额
				double totalLiab = Double.valueOf(incstateSheet.getTotalProfitEnd());
				//利息费用
				double interExpense = Double.valueOf(incstateSheet.getInterExpense());
				
				//息税前利润(EBIT) = 利润总额  + 利息费用
				String dbequrt = MathUtils.format2DecPoint(totalLiab + interExpense);
				
				yearBuilder.append(incstateSheet.getYear()).append(",");
				dataBuilder.append(dbequrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("息税前利润(EBIT)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//税息折旧及摊销前利润(EBITDA)
	public String EBITDA() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  
		
		String key = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(key.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(inBal);  
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (incstateSheetList != null && balanceSheetsList != null  && incstateSheetList.size() > 0 &&  incstateSheetList.size() == balanceSheetsList.size() ) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i< incstateSheetList.size(); i++) {
				IncstateSheet incstateSheet = incstateSheetList.get(i);
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//利润总额(净利润 + 所得税)
				double totalLiab = Double.valueOf(incstateSheet.getTotalProfitEnd());
				//利息费用
				double interExpense = Double.valueOf(incstateSheet.getInterExpense());
				// 固定资产折旧
				double fixedAssDepre = Double.valueOf(incstateSheet.getFixedAssDepre());
				//无形资产摊销
				double lntangAssetsAmortize = Double.valueOf(balanceSheet.getLntangAssetsAmortize());
				//长期待摊费用摊销
				double longPreAmort = Double.valueOf(incstateSheet.getLongPreAmort());
				//税息折旧及摊销前利润(EBITDA) = 净利润+所得税+固定资产折旧+无形资产摊销+长期待摊费用摊销+偿付利息所支付的现金	
				String EBITDA = MathUtils.format2DecPoint(totalLiab + interExpense + fixedAssDepre + lntangAssetsAmortize + longPreAmort);
				
				yearBuilder.append(incstateSheet.getYear()).append(",");
				dataBuilder.append(EBITDA).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("税息折旧及摊销前利润(EBITDA)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//利息保障倍数
	public String intcvr() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (incstateSheetList != null && incstateSheetList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i< incstateSheetList.size(); i++) {
				IncstateSheet incstateSheet = incstateSheetList.get(i);
				
				//利润总额
				double totalLiab = Double.valueOf(incstateSheet.getTotalProfitEnd());
				//利息费用
				double interExpense = Double.valueOf(incstateSheet.getInterExpense());
				
				if (interExpense == 0) continue;
				
				//利息保障倍数 = 税息前利润 /（利息费用　＋　资本化利息支出) 
				//由于资本化利息支出数据不在财报中体现具体数据，所以只取值财务报表中的-利息费用
				String intcvr = MathUtils.format2DecPoint((totalLiab + interExpense) / interExpense);
				
				yearBuilder.append(incstateSheet.getYear()).append(",");
				dataBuilder.append(intcvr).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("利息保障倍数");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//短期有息负债
	public String shortInterestDebt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String key = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(key.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(inBal);  
		
		Chart chart = new Chart();
		
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//短期借款
				double shortTermLoans = Double.valueOf(balanceSheet.getShortTermLoans());
				//应付票据
				double notePayable = Double.valueOf(balanceSheet.getNotePayable());
				//一年内到期的非流动负债
				double debitWithinYear = Double.valueOf(balanceSheet.getDebitWithinYear());
				
				//短期有息负债 = 短期借款+应付票据+一年内到期的长期借款(一年内到期的非流动负债)
				String shortInterestDebt = MathUtils.format2DecPoint((shortTermLoans + notePayable + debitWithinYear));
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(shortInterestDebt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("短期有息负债");
			chart.setText(code + " " + stockName);
		}
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//长期有息负债
	public String longInterestDebt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String key = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(key.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(inBal);  
		
		Chart chart = new Chart();
		
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//长期借款
				double longTermLoans = Double.valueOf(balanceSheet.getLongTermLoans());
				//应付债券
				double boundsPayAble = Double.valueOf(balanceSheet.getBoundsPayable());
							
				//长期有息负债 = 长期借款 + 应付债券
				String longInterestDebt = MathUtils.format2DecPoint(longTermLoans + boundsPayAble);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(longInterestDebt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("长期有息负债");
			chart.setText(code + " " + stockName);
		}
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//有息负债
	public String interestDebt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String key = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(key.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(inBal);  
		
		Chart chart = new Chart();
		
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//长期借款
				double longTermLoans = Double.valueOf(balanceSheet.getLongTermLoans());
				//应付债券
				double boundsPayAble = Double.valueOf(balanceSheet.getBoundsPayable());
				
				//短期借款
				double shortTermLoans = Double.valueOf(balanceSheet.getShortTermLoans());
				//应付票据
				double notePayable = Double.valueOf(balanceSheet.getNotePayable());
				//一年内到期的非流动负债
				double debitWithinYear = Double.valueOf(balanceSheet.getDebitWithinYear());
							
				//有息负债 = 长期有息负债 + 短期有息负债
				String interestDebt = MathUtils.format2DecPoint(longTermLoans + boundsPayAble + shortTermLoans + notePayable + debitWithinYear);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(interestDebt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("有息负债");
			chart.setText(code + " " + stockName);
		}
		request.put("chart", chart);
		
		return methodName;
	}
	
	//有息负债比率
	public String interestDebtRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String key = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(key.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(inBal);  
		
		Chart chart = new Chart();
		
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//长期借款
				double longTermLoans = Double.valueOf(balanceSheet.getLongTermLoans());
				//应付债券
				double boundsPayAble = Double.valueOf(balanceSheet.getBoundsPayable());
				
				//短期借款
				double shortTermLoans = Double.valueOf(balanceSheet.getShortTermLoans());
				//应付票据
				double notePayable = Double.valueOf(balanceSheet.getNotePayable());
				//一年内到期的非流动负债
				double debitWithinYear = Double.valueOf(balanceSheet.getDebitWithinYear());
				
				//期末负债总额
				double totalLiab = Double.valueOf(balanceSheet.getTotalLiabEnd());
				
				//有息负债 = 长期有息负债 + 短期有息负债
				double interestDebt = longTermLoans + boundsPayAble + shortTermLoans + notePayable + debitWithinYear;
				
				//有息负债比率 = 带息负债总额/负债总额	
				String interestDebtRatio = MathUtils.format2DecPoint(interestDebt / totalLiab);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(interestDebtRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("有息负债比率");
			chart.setText(code + " " + stockName);
		}
		request.put("chart", chart);
		
		return methodName;
	}
	
	//有形净值债务比率
	public String dbtaneQurt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String key = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(key.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(inBal);  
		
		Chart chart = new Chart();
		
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//期末负债总额
				double totalLiab = Double.valueOf(balanceSheet.getTotalLiabEnd());
				
				//股东权益(期末)
				double shareHolderEnd = Double.valueOf(balanceSheet.getShareHolderEnd());
				
				//无形资产
				double lntangAssets = Double.valueOf(balanceSheet.getLntangAssets()); 
				
				//商誉
				double goodWill = Double.valueOf(balanceSheet.getGoodWill());
				
				double diff = shareHolderEnd - lntangAssets - goodWill;
				
				if (diff == 0) continue;
				
				//有形净值债务比率 = 负债总额 /( 股东权益 - 无形资产净值 - 商誉 )		
				String dbtaneQurt = MathUtils.format2DecPoint((totalLiab / diff) * 100);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(dbtaneQurt).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("有形净值债务比率(%)");
			chart.setText(code + " " + stockName);
		}
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}
	
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
}
