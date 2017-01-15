package com.investdata.action.operaCapacity;

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
import com.investdata.dao.po.IncstateSheet;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.investdata.utils.MathUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 营运能力计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */

public class OperaCapacityAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(OperaCapacityAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	
	
	//应收账款周转率
	public String aRTrat() throws Exception {
		Jedis jedis = RedisCache.getJedis();
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  	
		
		String balKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(balKey.getBytes());
		List<BalanceSheet> balanceSheetList = ObjectsTranscoder.deserialize(inBal); 
		jedis.close();
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0 && balanceSheetList != null && incstateSheetList.size() == balanceSheetList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				BalanceSheet balSheet = balanceSheetList.get(i);
				
				//本期营业收入
				double busiIncomeThis = Double.parseDouble(incsSheet.getBusiIncomeThis());
				
				//期初应收账款
				double accRecableStart= Double.parseDouble(balSheet.getAccRecableStart());
				//期末应收账款
				double accRecableEnd= Double.parseDouble(balSheet.getAccRecableEnd());
				//平均应收账款
				double avgAccRecableEnd = (accRecableStart + accRecableEnd) / 2;
				
				if (avgAccRecableEnd == 0) continue;
				
				//投资收益 / 利润总额  * 100
				String aRTrat = MathUtils.format2DecPoint(busiIncomeThis / avgAccRecableEnd);
						
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(aRTrat).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("应收账款周转率");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//存货周转率
	public String invtrtrrat() throws Exception {
		Jedis jedis = RedisCache.getJedis();
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  	
		
		String balKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(balKey.getBytes());
		List<BalanceSheet> balanceSheetList = ObjectsTranscoder.deserialize(inBal); 
		jedis.close();
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0 && balanceSheetList != null && incstateSheetList.size() == balanceSheetList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				BalanceSheet balSheet = balanceSheetList.get(i);
				
				//营业成本
				double operatCost = Double.parseDouble(incsSheet.getOperatCost());
				
				//期初存货
				double goodsStart= Double.parseDouble(balSheet.getGoodsStart());
				
				//期末存货
				double goodsEnd= Double.parseDouble(balSheet.getGoodsEnd());
				
				//平均存货
				double avgGoods = (goodsStart + goodsEnd)  / 2;
				
				if (avgGoods == 0) continue;
				
				//营业成本/[（年初存货 + 年末存货）/2 ]
				String invtrtrrat = MathUtils.format2DecPoint(operatCost / avgGoods);
						
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(invtrtrrat).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("存货周转率");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//存货周转天数
	public String invtrtrday() throws Exception {
		Jedis jedis = RedisCache.getJedis();
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  	
		
		String balKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(balKey.getBytes());
		List<BalanceSheet> balanceSheetList = ObjectsTranscoder.deserialize(inBal); 
		jedis.close();
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0 && balanceSheetList != null && incstateSheetList.size() == balanceSheetList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				BalanceSheet balSheet = balanceSheetList.get(i);
				
				//营业成本
				double operatCost = Double.parseDouble(incsSheet.getOperatCost());
				
				//期初存货
				double goodsStart= Double.parseDouble(balSheet.getGoodsStart());
				
				//期末存货
				double goodsEnd= Double.parseDouble(balSheet.getGoodsEnd());
				
				//平均存货
				double avgGoods = (goodsStart + goodsEnd)  / 2;
				
				if (avgGoods ==0) continue;
				
				//营业成本/[（年初存货 + 年末存货）/2 ]
				double invtrtrrat = Double.valueOf(MathUtils.format2DecPoint(operatCost / avgGoods));
				
				if (invtrtrrat == 0) continue;
				
				String invtrtrday = MathUtils.format2DecPoint(360 / invtrtrrat);
				
						
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(invtrtrday).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("存货周转天数");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//应收账款周转天数
	public String aRTday() throws Exception {
		Jedis jedis = RedisCache.getJedis();
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  	
		
		String balKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(balKey.getBytes());
		List<BalanceSheet> balanceSheetList = ObjectsTranscoder.deserialize(inBal); 
		jedis.close();
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0 && balanceSheetList != null && incstateSheetList.size() == balanceSheetList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				BalanceSheet balSheet = balanceSheetList.get(i);
				
				//本期营业收入
				double busiIncomeThis = Double.parseDouble(incsSheet.getBusiIncomeThis());
				
				//期初应收账款
				double accRecableStart= Double.parseDouble(balSheet.getAccRecableStart());
				//期末应收账款
				double accRecableEnd= Double.parseDouble(balSheet.getAccRecableEnd());
				//平均应收账款
				double avgAccRecableEnd = (accRecableStart + accRecableEnd) / 2;
				
				if (avgAccRecableEnd == 0) continue;
				
				double aRTrat = Double.valueOf(MathUtils.format2DecPoint(busiIncomeThis / avgAccRecableEnd));
				
				if (aRTrat == 0) continue;
				
				String aRTday = MathUtils.format2DecPoint(360 / aRTrat);
						
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(aRTday).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("应收账款周转天数");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//流动资产周转率
	public String currat() throws Exception {
		Jedis jedis = RedisCache.getJedis();
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  	
		
		String balKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(balKey.getBytes());
		List<BalanceSheet> balanceSheetList = ObjectsTranscoder.deserialize(inBal); 
		jedis.close();
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0 && balanceSheetList != null && incstateSheetList.size() == balanceSheetList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				BalanceSheet balSheet = balanceSheetList.get(i);
				
				//本期营业收入
				double busiIncomeThis = Double.parseDouble(incsSheet.getBusiIncomeThis());
				
				//期初流动资产
				double liquidAssetsStart= Double.parseDouble(balSheet.getLiquidAssetsStart());
				//期末流动资产
				double liquidAssetsEnd= Double.parseDouble(balSheet.getLiquidAssetsEnd());
				//平均流动资产
				double avgliquidAssets = (liquidAssetsStart + liquidAssetsEnd) / 2;
				
				if (avgliquidAssets ==0 )  continue;
				
				String currat = MathUtils.format2DecPoint(busiIncomeThis / avgliquidAssets);
										
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(currat).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("流动资产周转率");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//固定资产周转率
	public String fixassrat() throws Exception {
		Jedis jedis = RedisCache.getJedis();
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  	
		
		String balKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(balKey.getBytes());
		List<BalanceSheet> balanceSheetList = ObjectsTranscoder.deserialize(inBal); 
		jedis.close();
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0 && balanceSheetList != null && incstateSheetList.size() == balanceSheetList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				BalanceSheet balSheet = balanceSheetList.get(i);
				
				//本期营业收入
				double busiIncomeThis = Double.parseDouble(incsSheet.getBusiIncomeThis());
				
				//期初固定资产
				double fixedAssetsStart= Double.parseDouble(balSheet.getFixedAssetsStart());
				//期末固定资产
				double fixedAssetsEnd= Double.parseDouble(balSheet.getFixedAssetsEnd());
				//平均流动资产
				double avgfixedAssets = (fixedAssetsStart + fixedAssetsEnd) / 2;
				
				if (avgfixedAssets ==0) continue;
				
				String fixassrat = MathUtils.format2DecPoint(busiIncomeThis / avgfixedAssets);
										
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(fixassrat).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("固定资产周转率");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//总资产周转率
	public String totassrat() throws Exception {
		Jedis jedis = RedisCache.getJedis();
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  	
		
		String balKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] inBal = jedis.get(balKey.getBytes());
		List<BalanceSheet> balanceSheetList = ObjectsTranscoder.deserialize(inBal); 
		jedis.close();
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0 && balanceSheetList != null && incstateSheetList.size() == balanceSheetList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				BalanceSheet balSheet = balanceSheetList.get(i);
				
				//本期营业收入
				double busiIncomeThis = Double.parseDouble(incsSheet.getBusiIncomeThis());
				
				//期初固定资产
				double totalAssStart= Double.parseDouble(balSheet.getTotalAssStart());
				//期末固定资产
				double totalAssEnd= Double.parseDouble(balSheet.getTotalAssEnd());
				//平均流动资产
				double avgTotalAss = (totalAssStart + totalAssEnd) / 2;
				
				if (avgTotalAss == 0) continue;
				
				String totassrat = MathUtils.format2DecPoint(busiIncomeThis / avgTotalAss);
										
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(totassrat).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("总资产周转率");
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
