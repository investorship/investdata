package com.investdata.action.cashFlow;

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
import com.investdata.dao.po.IncstateSheet;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.investdata.utils.MathUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 现金流量计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */

public class CashFlowAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(CashFlowAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	private static Jedis jedis = RedisCache.getJedis();
	
	
	
	//经营现金净流量
	public String operBusiCashRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		String cashFlowKey = code + "#" + Const.CASHFLOWDATA_KEY;
		byte[] in = jedis.get(cashFlowKey.getBytes());
		List<CashFlowSheet> cashFlowList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (cashFlowList != null && cashFlowList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<cashFlowList.size();  i++) {
				CashFlowSheet cashFlowSheet = cashFlowList.get(i);
				
				//经营性获取产生的现金流量净额
				double operaActiveCash = Double.parseDouble(cashFlowSheet.getOperaActiveCash());
			
				String operBusiCashRatio = MathUtils.format2DecPoint(operaActiveCash);
						
				yearBuilder.append(cashFlowSheet.getYear()).append(",");
				dataBuilder.append(operBusiCashRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("经营现金净流量");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	//经营现金净流量/净利润
	public String cashFlowProfits() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		String cashFlowKey = code + "#" + Const.CASHFLOWDATA_KEY;
		byte[] in = jedis.get(cashFlowKey.getBytes());
		List<CashFlowSheet> cashFlowList = ObjectsTranscoder.deserialize(in);  		
		
		String incKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] incIn = jedis.get(incKey.getBytes());
		List<IncstateSheet> incSheetList = ObjectsTranscoder.deserialize(incIn); 
		
		Chart chart = new Chart();
		
		if (cashFlowList != null && cashFlowList.size() > 0 && incSheetList != null && incSheetList.size() == cashFlowList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<cashFlowList.size();  i++) {
				CashFlowSheet cashFlowSheet = cashFlowList.get(i);
				IncstateSheet incSheet = incSheetList.get(i);
				
				//经营性获取产生的现金流量净额
				double operaActiveCash = Double.parseDouble(cashFlowSheet.getOperaActiveCash());
				//本期净利润
				double netProfitsThis = Double.parseDouble(incSheet.getNetProfitsThis());
				
				if (netProfitsThis == 0) continue;
				
				String operBusiCashRatio = MathUtils.format2DecPoint(operaActiveCash / netProfitsThis * 100);
				
				yearBuilder.append(cashFlowSheet.getYear()).append(",");
				dataBuilder.append(operBusiCashRatio).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
		
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("经营现金净流量/净利润(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//主营业务现金比率
	public String coreBusiCashRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		String cashFlowKey = code + "#" + Const.CASHFLOWDATA_KEY;
		byte[] in = jedis.get(cashFlowKey.getBytes());
		List<CashFlowSheet> cashFlowList = ObjectsTranscoder.deserialize(in);  		
		
		String incKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] incIn = jedis.get(incKey.getBytes());
		List<IncstateSheet> incSheetList = ObjectsTranscoder.deserialize(incIn); 
		
		Chart chart = new Chart();
		
		if (cashFlowList != null && cashFlowList.size() > 0 && incSheetList != null && incSheetList.size() == cashFlowList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<cashFlowList.size();  i++) {
				CashFlowSheet cashFlowSheet = cashFlowList.get(i);
				IncstateSheet incSheet = incSheetList.get(i);
				
				//经营性获取产生的现金流量净额
				double operaActiveCash = Double.parseDouble(cashFlowSheet.getOperaActiveCash());
				//本期营业收入
				double busiIncomeThis = Double.parseDouble(incSheet.getBusiIncomeThis());
				
				if (busiIncomeThis == 0) continue;
				
				//主营业务现金比率 = 经营性获取产生的现金流量净额 / 本期营业收入
				String coreBusiCashRatio = MathUtils.format2DecPoint(operaActiveCash / busiIncomeThis * 100);
				
				yearBuilder.append(cashFlowSheet.getYear()).append(",");
				dataBuilder.append(coreBusiCashRatio).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("主营业务现金比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//现金及其等价物净增加额
	public String cashFlowInc() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		String cashFlowKey = code + "#" + Const.CASHFLOWDATA_KEY;
		byte[] in = jedis.get(cashFlowKey.getBytes());
		List<CashFlowSheet> cashFlowList = ObjectsTranscoder.deserialize(in);  		
	
		
		Chart chart = new Chart();
		
		if (cashFlowList != null && cashFlowList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<cashFlowList.size();  i++) {
				CashFlowSheet cashFlowSheet = cashFlowList.get(i);				
				//现金及现金等价物增加额
				double cashAndCashequ = Double.parseDouble(cashFlowSheet.getCashAndCashequ());
				
				String cashFlowInc = MathUtils.format2DecPoint(cashAndCashequ);
				
				yearBuilder.append(cashFlowSheet.getYear()).append(",");
				dataBuilder.append(cashFlowInc).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("现金及其等价物净增加额");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//总资产现金回收率
	public String cashAndAssetRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		String cashFlowKey = code + "#" + Const.CASHFLOWDATA_KEY;
		byte[] in = jedis.get(cashFlowKey.getBytes());
		List<CashFlowSheet> cashFlowList = ObjectsTranscoder.deserialize(in);  	
		
		String balKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balKey.getBytes());
		List<BalanceSheet> balSheetList = ObjectsTranscoder.deserialize(balIn);  	
		
		
		Chart chart = new Chart();
		
		if (cashFlowList != null && cashFlowList.size() > 0 && balSheetList != null && balSheetList.size() == cashFlowList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<cashFlowList.size();  i++) {
				CashFlowSheet cashFlowSheet = cashFlowList.get(i);	
				BalanceSheet  balSheet = balSheetList.get(i);
				
				//经营性获取产生的现金流量净额
				double operaActiveCash = Double.parseDouble(cashFlowSheet.getOperaActiveCash());
				//期初资产总额
				double totalAssStart = Double.parseDouble(balSheet.getTotalAssStart());
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				
				double totalAssAVG = (totalAssStart + totalAssEnd) / 2 ;
				
				//平均资产总额
				String cashAndAssetRatio = MathUtils.format2DecPoint(operaActiveCash / totalAssAVG * 100);
				
				yearBuilder.append(cashFlowSheet.getYear()).append(",");
				dataBuilder.append(cashAndAssetRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("总资产现金回收率");
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
