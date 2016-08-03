package com.investdata.action.pershare;

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
 * 每股指标计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */

public class PerShareAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(PerShareAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	private static Jedis jedis = RedisCache.getJedis();
	
	//每股收益
	public String EPS() throws Exception {
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String methodName = (String)ActionContext.getContext().get("methodName");
		String compxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		StringBuilder epsData = new StringBuilder();
		StringBuilder yearData = new StringBuilder();
		
		for (GendataSheet gs : genDataList) {
			epsData.append(gs.getEps()).append(",");
			yearData.append(gs.getYear()).append(",");
		}
		
		//去掉最后一个逗号
		if (genDataList != null && genDataList.size() > 1) {
			epsData.deleteCharAt(epsData.length() -1);
			yearData.deleteCharAt(yearData.length() -1);			
		}
		
		Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
		String stockName = stockCodeMapping.get(code);
		
		//封装图标信息
		Chart EPSchart = new Chart();
		EPSchart.setxAxis(yearData.toString());
		EPSchart.setText(code + " " + stockName);
//		EPSchart.setLegendData(indexName);
		EPSchart.setLegendData("基本每股收益");
		
		EPSchart.setData(epsData.toString());
		request.put("chart", EPSchart);
		
		return methodName;
	}
	
	//稀释每股收益
	public String dilutEPS() throws Exception {
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		StringBuilder dataBuilder = new StringBuilder();
		StringBuilder yearBuilder = new StringBuilder();
		
		for (GendataSheet gs : genDataList) {
			dataBuilder.append(gs.getEpsDiluted()).append(","); //取稀释每股收益值
			yearBuilder.append(gs.getYear()).append(",");
		}
		
		//去掉最后一个逗号
		if (genDataList != null && genDataList.size() > 1) {
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			yearBuilder.deleteCharAt(yearBuilder.length() -1);			
		}
		
		Chart chart = new Chart();
		chart.setxAxis(yearBuilder.toString());
		chart.setData(dataBuilder.toString());
		Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
		String stockName = stockCodeMapping.get(code);
		chart.setText(code + " " + stockName);
		chart.setLegendData("稀释每股收益");
		
		request.put("chart", chart);
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		return methodName;
	}
	
	//每股营业收入
	public String mincmPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度当年营业收入数据
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incSheetsList =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && incSheetsList != null  && genDataList.size() > 0 && genDataList.size() == incSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incSheetsList.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				IncstateSheet incSheet = incSheetsList.get(i);
				
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				double busiIncomeThis = Double.valueOf(incSheet.getBusiIncomeThis());
				
				//每股营业收入=营业收入/期末总股本
				String mincmPs = MathUtils.format2DecPoint(busiIncomeThis / totoalStocks);
				
				yearBuilder.append(incSheet.getYear()).append(",");
				dataBuilder.append(mincmPs).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股营业收入");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//每股营业利润
	public String opeprfPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度当年营业利润数据
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incSheetsList =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && incSheetsList != null  && genDataList.size() > 0 && genDataList.size() == incSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incSheetsList.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				IncstateSheet incSheet = incSheetsList.get(i);
				
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				double operaProfitsThis = Double.valueOf(incSheet.getOperaProfitsThis());
				
				//每股营业利润=本年度营业利润/期末总股本
				String opeprfPS = MathUtils.format2DecPoint(operaProfitsThis / totoalStocks);
				
				yearBuilder.append(incSheet.getYear()).append(",");
				dataBuilder.append(opeprfPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股营业利润");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//每股资本公积
	public String capsurfdPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度当年资本公积数据
		String incCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<BalanceSheet> balanceSheetsList =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && balanceSheetsList != null  && genDataList.size() > 0 && genDataList.size() == balanceSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				double capitalSurplus = Double.valueOf(balanceSheet.getCapitalSurplus());
				
				//每股资本公积=资本公积/期末总股本
				String capsurfdPS = MathUtils.format2DecPoint(capitalSurplus / totoalStocks);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(capsurfdPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股资本公积");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//每股盈余公积
	public String surrefdPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度当年盈余公积数据
		String incCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<BalanceSheet> balanceSheetsList =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && balanceSheetsList != null  && genDataList.size() > 0 && genDataList.size() == balanceSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				double surplusReserve = Double.valueOf(balanceSheet.getSurplusReserve());
				
				//每股盈余公积=盈余公积/期末总股本
				String surrefdPS = MathUtils.format2DecPoint(surplusReserve / totoalStocks);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(surrefdPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股盈余公积");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//每股公积金
	public String accumfdPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度当年盈余公积数据 + 盈余公积数据
		String incCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<BalanceSheet> balanceSheetsList =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && balanceSheetsList != null  && genDataList.size() > 0 && genDataList.size() == balanceSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				//盈余公积
				double surplusReserve = Double.valueOf(balanceSheet.getSurplusReserve());
				//资本公积
				double capitalSurplus = Double.valueOf(balanceSheet.getCapitalSurplus());
				
				double sumPS = surplusReserve + capitalSurplus;
				
				//每股公积=资本公积 + 盈余公积 / 期末总股本
				String accumfdPS = MathUtils.format2DecPoint(sumPS / totoalStocks);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(accumfdPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股公积金");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//每股未分配利润
	public String undivprfPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度未分配利润数据
		String incCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<BalanceSheet> balanceSheetsList =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && balanceSheetsList != null  && genDataList.size() > 0 && genDataList.size() == balanceSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				double retainEarnings = Double.valueOf(balanceSheet.getRetainEarnings());
				
				
				//每股未分配利润= 未分配利润  / 期末总股本
				String undivprfPS = MathUtils.format2DecPoint(retainEarnings / totoalStocks);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(undivprfPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股未分配利润");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//每股留存收益
	public String retearPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度未分配利润数据
		String incCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<BalanceSheet> balanceSheetsList =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && balanceSheetsList != null  && genDataList.size() > 0 && genDataList.size() == balanceSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				//盈余公积
				double surplusReserve = Double.valueOf(balanceSheet.getSurplusReserve());
				//未分配利润
				double retainEarnings = Double.valueOf(balanceSheet.getRetainEarnings());
				
				double sumPs = surplusReserve + retainEarnings;
				
				//每股留存收益= 未分配利润 + 盈余公积 / 期末总股本
				String retearPS = MathUtils.format2DecPoint(sumPs / totoalStocks);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(retearPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股留存收益");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//每股经营活动现金流量
	public String opeCFPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度 经营活动产生的现金流量净额
		String incCompxKey = code + "#" + Const.CASHFLOWDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<CashFlowSheet> cashFlowSheets =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && cashFlowSheets != null  && genDataList.size() > 0 && genDataList.size() == cashFlowSheets.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<cashFlowSheets.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				CashFlowSheet canshFlowSheet = cashFlowSheets.get(i);
				
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				//经营活动产生的现金流量净额
				double operaActiveCash = Double.valueOf(canshFlowSheet.getOperaActiveCash());
				
				//每股经营活动现金流量= 经营活动产生的现金流量净额  / 期末总股本
				String opeCFPS = MathUtils.format2DecPoint(operaActiveCash / totoalStocks);
				
				yearBuilder.append(canshFlowSheet.getYear()).append(",");
				dataBuilder.append(opeCFPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股经营活动现金流量");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//每股净现金流量
	public String CFPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度 现金及现金等价物净增加额
		String incCompxKey = code + "#" + Const.CASHFLOWDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<CashFlowSheet> cashFlowSheets =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && cashFlowSheets != null  && genDataList.size() > 0 && genDataList.size() == cashFlowSheets.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<cashFlowSheets.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				CashFlowSheet canshFlowSheet = cashFlowSheets.get(i);
				
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				//现金及现金等价物净增加额
				double cashAndCashequ = Double.valueOf(canshFlowSheet.getCashAndCashequ());
				
				//每股净现金流量	现金及现金等价物净增加额  / 期末总股本
				String CFPS = MathUtils.format2DecPoint(cashAndCashequ / totoalStocks);
				
				yearBuilder.append(canshFlowSheet.getYear()).append(",");
				dataBuilder.append(CFPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股净现金流量");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//每股净资产
	public String NAPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度 资产总额 和负债总额数据
		String incCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<BalanceSheet> balanceSheets =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && balanceSheets != null  && genDataList.size() > 0 && genDataList.size() == balanceSheets.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheets.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				BalanceSheet balanceSheet = balanceSheets.get(i);
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				//期末资产总额
				double totalAssEnd = Double.valueOf(balanceSheet.getTotalAssEnd());
				//期末负债总额
				double totalLiab = Double.valueOf(balanceSheet.getTotalLiabEnd());
				//净资产 = 资产总额 - 负债总额
				double  netAss = totalAssEnd - totalLiab;
				
				//每股净资产 = 	净资产/期末普通股股数	
				String NAPS = MathUtils.format2DecPoint(netAss / totoalStocks);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(NAPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股净资产");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}

	
	//每股息税前利润
	public String EBITPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		//获取各年度总股本数据
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  
		
		//获取各年度 利润总额 和利息费用等数据
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] inc = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheets =  ObjectsTranscoder.deserialize(inc);
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (genDataList != null && incstateSheets != null  && genDataList.size() > 0 && genDataList.size() == incstateSheets.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheets.size(); i++) {
				GendataSheet genSheet = genDataList.get(i);
				IncstateSheet incstateSheet = incstateSheets.get(i);
				int totoalStocks = Integer.valueOf(genSheet.getTotalStocks());
				//期末利润总额
				double totalAssEnd = Double.valueOf(incstateSheet.getTotalProfitEnd());
				//财务费用中 利息支出部分
				double interExpense = Double.valueOf(incstateSheet.getInterExpense());
				//息税前利润 = 利润总额 (净利润 + 所得税) + 利息费用
				double  EBIT = totalAssEnd + interExpense;
				//每股息税前利润 = 息税前利润  / 期末普通股股数	
				String EBITPS = MathUtils.format2DecPoint(EBIT / totoalStocks);
				
				yearBuilder.append(incstateSheet.getYear()).append(",");
				dataBuilder.append(EBITPS).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("每股息税前利润");
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
