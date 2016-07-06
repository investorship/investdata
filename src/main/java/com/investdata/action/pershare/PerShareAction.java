package com.investdata.action.pershare;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;

import redis.clients.jedis.Jedis;

import com.investdata.common.BaseAction;
import com.investdata.common.Const;
import com.investdata.dao.po.Chart;
import com.investdata.dao.po.GendataSheet;
import com.investdata.dao.po.IncstateSheet;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.investdata.utils.MathUtils;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;

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
