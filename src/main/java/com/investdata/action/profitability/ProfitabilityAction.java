package com.investdata.action.profitability;

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
 * 盈利能力计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */

public class ProfitabilityAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(ProfitabilityAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	private static Jedis jedis = RedisCache.getJedis();
	
	
	
	//加权平均净资产收益率
	public String ROE() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  		
		Chart chart = new Chart();
		
		if (genDataList != null && genDataList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<genDataList.size();  i++) {
				GendataSheet genSheet = genDataList.get(i);
				//加权平均净资产收益率
				double roeWa = Double.valueOf(genSheet.getRoeWa());
						
				yearBuilder.append(genSheet.getYear()).append(",");
				dataBuilder.append(roeWa).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("加权平均净资产收益率");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//加权平均净资产收益率(扣非)
	public String ROEcut() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String genCompxKey = code + "#" + Const.GENDATA_KEY;
		byte[] in = jedis.get(genCompxKey.getBytes());
		List<GendataSheet> genDataList = ObjectsTranscoder.deserialize(in);  		
		Chart chart = new Chart();
		
		if (genDataList != null && genDataList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<genDataList.size();  i++) {
				GendataSheet genSheet = genDataList.get(i);
				//加权平均净资产收益率(扣非)
				double roeWa = Double.valueOf(genSheet.getRoeWaKf());
						
				yearBuilder.append(genSheet.getYear()).append(",");
				dataBuilder.append(roeWa).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("加权平均净资产收益率(扣非)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//资产净利率
	public String ROA() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(balIn);  	
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && incstSheetsList.size() > 0 && balSheetsList !=null && incstSheetsList.size() == balSheetsList.size() ) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				BalanceSheet balSheet = balSheetsList.get(i);
				
				//期初资产总额
				double totalAssStart = Double.parseDouble(balSheet.getTotalAssStart());
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				
				//平均资产总额
				double avgAssEnd = (totalAssEnd + totalAssStart) / 2 ;
				
				if (avgAssEnd == 0) continue;
				
				//本期净利润
				double profit = Double.parseDouble(incstSheet.getNetProfitsThis());
				
				String ROA = MathUtils.format2DecPoint((profit / avgAssEnd) * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(ROA).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("资产净利率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//资产利润率
	public String assetsProRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(balIn);  	
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && incstSheetsList.size() > 0 && balSheetsList !=null && incstSheetsList.size() == balSheetsList.size() ) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				BalanceSheet balSheet = balSheetsList.get(i);
				
				//期初资产总额
				double totalAssStart = Double.parseDouble(balSheet.getTotalAssStart());
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				
				//平均资产总额
				double avgAssEnd = (totalAssEnd + totalAssStart) / 2 ;
				
				if (avgAssEnd == 0) continue;
				
				//本期利润总额
				double totalProfitEnd = Double.parseDouble(incstSheet.getTotalProfitEnd());
				
				String assetsProRatio = MathUtils.format2DecPoint((totalProfitEnd / avgAssEnd) * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(assetsProRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("资产利润率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//毛利率
	public String grossProfit() throws Exception {
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
				
				//本期营业收入
				double busiInComeThis = Double.parseDouble(incstSheet.getBusiIncomeThis());
				
				if (busiInComeThis == 0) continue;
				
				//营业成本
				double operatCost = Double.parseDouble(incstSheet.getOperatCost());
				
				//（营业收入-营业成本）/营业收入*100%	
				String grossProfit = MathUtils.format2DecPoint((busiInComeThis - operatCost ) / busiInComeThis * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(grossProfit).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("毛利率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//净利率
	public String splashes() throws Exception {
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
				
				//本期营业收入
				double busiInComeThis = Double.parseDouble(incstSheet.getBusiIncomeThis());
				//净利润
				double netProfitsThis = Double.parseDouble(incstSheet.getNetProfitsThis());
				
				if (busiInComeThis == 0) continue;
				
				//净利润/营业收入 *100%	
				String grossProfit = MathUtils.format2DecPoint(netProfitsThis / busiInComeThis * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(grossProfit).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("净利率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//净利率(扣非)
	public String splashesKF() throws Exception {
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
				
				//本期营业收入
				double busiInComeThis = Double.parseDouble(incstSheet.getBusiIncomeThis());
				//净利润(扣非)
				double netProfitsThis = Double.parseDouble(incstSheet.getNetProfitsKfThis());
				
				if (busiInComeThis == 0) continue;
				
				//净利润(扣非) /营业收入 *100%	
				String grossProfit = MathUtils.format2DecPoint(netProfitsThis / busiInComeThis * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(grossProfit).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("净利率(扣非)(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//成本费用净利率
	public String costExpProRatio() throws Exception {
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
				//营业成本
				double operatCost = Double.parseDouble(incstSheet.getOperatCost());
				//营业税金及附加
				double busiTaxSurcharge = Double.parseDouble(incstSheet.getBusiTaxSurcharge());
				//管理费用
				double mgrConstsThis = Double.parseDouble(incstSheet.getMgrConstsThis());
				//本期财务费用
				double finConstsThis = Double.parseDouble(incstSheet.getFinanceConstsThis());
				//本期销售费用
				double marketConstsThis = Double.parseDouble(incstSheet.getMarketConstsThis());
				//净利润
				double netProfitsThis = Double.parseDouble(incstSheet.getNetProfitsThis());
				//成本费用总额=营业成本+营业税金及附加+管理费用+财务费用 +销售费用
				double totalConsts = operatCost + busiTaxSurcharge + mgrConstsThis + finConstsThis + marketConstsThis;
				
				if (totalConsts == 0) continue;
				
				//成本费用净利率 = 净利润/成本费用总额		
				String costExpProRatio = MathUtils.format2DecPoint(netProfitsThis / totalConsts * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(costExpProRatio).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("成本费用净利率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//资产息税前利润率
	public String ebitRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  		
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(balIn);  
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && incstSheetsList.size() > 0 && balSheetsList != null && incstSheetsList.size() == balSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				BalanceSheet balSheet = balSheetsList.get(i);
				
				//利润总额
				double totalProfitEnd = Double.parseDouble(incstSheet.getTotalProfitEnd());
				//利息费用
				double interExpense = Double.valueOf(incstSheet.getInterExpense());
				
				//期初资产总额
				double totalAssStart = Double.parseDouble(balSheet.getTotalAssStart());
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				
				//平均资产总额
				double avgAssEnd = (totalAssEnd + totalAssStart) / 2 ;
				
				if (avgAssEnd == 0) continue;
				
				//资产息税前利润率 = 息税前利润/资产平均总额	
				String costExpProRatio = MathUtils.format2DecPoint((totalProfitEnd + interExpense) / avgAssEnd * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(costExpProRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("资产息税前利润率(%)");
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
