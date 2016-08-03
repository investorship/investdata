package com.investdata.action.growthCapab;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;

import redis.clients.jedis.Jedis;

import com.investdata.common.BaseAction;
import com.investdata.common.Const;
import com.investdata.dao.po.BalanceSheet;
import com.investdata.dao.po.Chart;
import com.investdata.dao.po.GendataSheet;
import com.investdata.dao.po.IncstateSheet;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.investdata.utils.MathUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 成长能力计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */

public class GrowthCapabAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(GrowthCapabAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	private static Jedis jedis = RedisCache.getJedis();
	
	
	
	//净利润增长率
	public String netprfgrrt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && incstSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				
				//上期净利润
				double netprofitsLast = Double.parseDouble(incstSheet.getNetProfitsLast());
				//当期净利润
				double netprofitsThis = Double.parseDouble(incstSheet.getNetProfitsThis());
				//净利润增长率 = (当期净利润-上期净利润) /上期净利润  *  100%
				String netprfgrrt = MathUtils.format2DecPoint((netprofitsThis - netprofitsLast) / netprofitsLast  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(netprfgrrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("净利润增长率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
		
		
	//净利润增长率(扣非)
	public String netprfgrrtKF() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && incstSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				//上期净利润
				double netprofitsKfLast = Double.parseDouble(incstSheet.getNetProfitsKfLast());
				//当期净利润
				double netprofitsKfThis = Double.parseDouble(incstSheet.getNetProfitsKfThis());
				//净利润增长率 = (当期净利润-上期净利润) /上期净利润  *  100%
				String netprfgrrtKF = MathUtils.format2DecPoint((netprofitsKfThis - netprofitsKfLast) / netprofitsKfLast  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(netprfgrrtKF).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("净利润增长率(扣非)(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//营业利润增长率
	public String opeprfgrrt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && incstSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				//上期营业利润
				double operaProfitsLast = Double.parseDouble(incstSheet.getOperaProfitsLast());
				//当期营业利润
				double operaProfitsThis = Double.parseDouble(incstSheet.getOperaProfitsThis());
				//营业利润增长率 = (当期营业利润-上期营业利润) /上期营业利润  *  100%
				String opeprfgrrt = MathUtils.format2DecPoint((operaProfitsThis - operaProfitsLast) / operaProfitsLast  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(opeprfgrrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("营业利润增长率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//利润总额增长率
	public String totprfgrrt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && incstSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				//上期利润总额
				double totalProfitStart = Double.parseDouble(incstSheet.getTotalProfitStart());
				//当期利润总额
				double totalProfitEnd = Double.parseDouble(incstSheet.getTotalProfitEnd());
				//利润总额增长率 = (当期营业利润-上期营业利润) /上期营业利润  *  100%
				String opeprfgrrt = MathUtils.format2DecPoint((totalProfitEnd - totalProfitStart) / totalProfitStart  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(opeprfgrrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("利润总额增长率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//营业收入增长率
	public String opeincmgrrt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && incstSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				//上期营业总收入
				double totalBusiIncLast = Double.parseDouble(incstSheet.getTotalBusiIncLast());
				//当期营业总收入
				double totalBusiIncThis = Double.parseDouble(incstSheet.getTotalBusiIncThis());
				//营业收入增长率 = (当期营业总收入-上期营业总收入) /上期营业总收入  *  100%
				String opeincmgrrt = MathUtils.format2DecPoint((totalBusiIncThis - totalBusiIncLast) / totalBusiIncLast  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(opeincmgrrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("营业收入增长率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//净资产增长率
	public String netassgrrt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (balSheetsList != null && balSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balSheetsList.size();  i++) {
				BalanceSheet balSheet = balSheetsList.get(i);
				
				//期初资产总额
				double totalAssStart = Double.parseDouble(balSheet.getTotalAssStart());
				//期初负债总额
				double totalLiabStart= Double.parseDouble(balSheet.getTotalLiabStart());
				
				//期初净资产
				double startNetassgrrt = totalAssStart - totalLiabStart;
				
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				//期末负债总额
				double totalLiabEnd= Double.parseDouble(balSheet.getTotalLiabEnd());
				
				//期末净资产
				double endNetassgrrt = totalAssEnd - totalLiabEnd;

				//净资产增长率 = (期末净资产 - 期初净资产) / 期初净资产  *  100%
				String netassgrrt = MathUtils.format2DecPoint((endNetassgrrt - startNetassgrrt) / startNetassgrrt  * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(netassgrrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("净资产增长率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//总资产增长率
	public String totassgrrt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (balSheetsList != null && balSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balSheetsList.size();  i++) {
				BalanceSheet balSheet = balSheetsList.get(i);
				
				//期初资产总额
				double totalAssStart = Double.parseDouble(balSheet.getTotalAssStart());
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				
				
				//总资产增长率 = (期末资产总额 - 期初资产总额) / 期初资产总额  *  100%
				String netassgrrt = MathUtils.format2DecPoint((totalAssEnd - totalAssStart) / totalAssStart  * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(netassgrrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("总资产增长率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	
	//股东权益相对年初增长率
	public String shareHolderEquity() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (balSheetsList != null && balSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balSheetsList.size();  i++) {
				BalanceSheet balSheet = balSheetsList.get(i);
				
				//期初股东权益
				double shareHolderStart = Double.parseDouble(balSheet.getShareHolderStart());
				//期末股东权益
				double shareHolderEnd = Double.parseDouble(balSheet.getShareHolderEnd());
				
				//总资产增长率 = (期末资产总额 - 期初资产总额) / 期初资产总额  *  100%
				String shareHolderEquity = MathUtils.format2DecPoint((shareHolderEnd - shareHolderStart) / shareHolderStart  * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(shareHolderEquity).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("股东权益相对年初增长率(%)");
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
