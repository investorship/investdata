package com.investdata.action.otherConcerns;

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
 * 其他关注点计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */

public class OtherConcernsAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(OtherConcernsAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	private IncstateSheet incstSheet;
	private static Jedis jedis = RedisCache.getJedis();
	
	
	
	//三项费用率
	public String threeExpRatio() throws Exception {
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
				//上期销售费用
				double marketConstsLast = Double.parseDouble(incstSheet.getMarketConstsLast());
				//本期销售费用
				double marketConstsThis = Double.parseDouble(incstSheet.getMarketConstsThis());
				//上期财务费用
				double financeConstsLast = Double.parseDouble(incstSheet.getFinanceConstsLast());
				//本期财务费用
				double financeConstsThis = Double.parseDouble(incstSheet.getFinanceConstsThis());
				//上期管理费用
				double mgrConstsLast = Double.parseDouble(incstSheet.getMgrConstsLast());
				//本期管理费用
				double mgrConstsThis = Double.parseDouble(incstSheet.getMgrConstsThis());
				
				double constsLast = marketConstsLast + financeConstsLast + mgrConstsLast;
				double constsThis = marketConstsThis + financeConstsThis + mgrConstsThis;
				
				if (constsLast == 0) continue;
				
				double diff = constsThis - constsLast;
			
				String netprfgrrt = MathUtils.format2DecPoint(diff / constsLast  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(netprfgrrt).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("三项费用率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//管理费用率
	public String mgrExpRatio() throws Exception {
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
				
				//上期管理费用
				double mgrConstsLast = Double.parseDouble(incstSheet.getMgrConstsLast());
				//本期管理费用
				double mgrConstsThis = Double.parseDouble(incstSheet.getMgrConstsThis());
				
				if (mgrConstsLast == 0) continue;
				
				double diff = mgrConstsThis - mgrConstsLast;
			
				String netprfgrrt = MathUtils.format2DecPoint(diff / mgrConstsLast  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(netprfgrrt).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("管理费用率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//销售费用率
	public String salExpRatio() throws Exception {
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
				
				//上期销售费用
				double marketConstsLast = Double.parseDouble(incstSheet.getMarketConstsLast());
				//本期销售费用
				double marketConstsThis = Double.parseDouble(incstSheet.getMarketConstsThis());
				
				double diff = marketConstsThis - marketConstsLast;
				
				if (marketConstsLast == 0) continue;
			
				String netprfgrrt = MathUtils.format2DecPoint(diff / marketConstsLast  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(netprfgrrt).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("销售费用率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}

	//财务费用率
	public String financeExpRatio() throws Exception {
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
				
				//上期财务费用
				double financeConstsLast = Double.parseDouble(incstSheet.getFinanceConstsLast());
				//本期财务费用
				double financeConstsThis = Double.parseDouble(incstSheet.getFinanceConstsThis());
				
				double diff = financeConstsThis - financeConstsLast;
				
				if (financeConstsLast == 0) continue;
			
				String netprfgrrt = MathUtils.format2DecPoint(diff / financeConstsLast  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				
				//考虑了财务费用为负数的情况，利息收入 >利息支出，如果今年减去年 值为负数就就说明是下降的，数据应该取负值，图形
				//显示更直观。
				if (diff < 0) { 
					dataBuilder.append(String.valueOf( 0 - Math.abs(Double.valueOf(netprfgrrt)))).append(",");					
				}else {
					dataBuilder.append(Math.abs(Double.valueOf(netprfgrrt))).append(",");	
				}
				
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("财务费用率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//应收/营业收入
	public String recAcctBusiRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(balIn);
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && balSheetsList != null && incstSheetsList.size() > 0 && balSheetsList.size() == incstSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				BalanceSheet  balSheet = balSheetsList.get(i);
				
				//本期营业收入
				double busiIncThis = Double.parseDouble(incstSheet.getBusiIncomeThis());
				//期末应收账款
				double accRecableEnd = Double.parseDouble(balSheet.getAccRecableEnd());
				
				if (busiIncThis == 0) continue;
				
				String recAcctBusiRatio = MathUtils.format2DecPoint(accRecableEnd / busiIncThis  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(recAcctBusiRatio).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("应收/营业收入(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//预收/营业收入
	public String advanceAcctBusiRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String incstCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incstCompxKey.getBytes());
		List<IncstateSheet> incstSheetsList = ObjectsTranscoder.deserialize(in);  
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(balIn);
		
		Chart chart = new Chart();
		
		if (incstSheetsList != null && balSheetsList != null && incstSheetsList.size() > 0 && balSheetsList.size() == incstSheetsList.size()) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstSheetsList.size();  i++) {
				IncstateSheet incstSheet = incstSheetsList.get(i);
				BalanceSheet  balSheet = balSheetsList.get(i);
				
				//本期营业收入
				double busiIncThis = Double.parseDouble(incstSheet.getBusiIncomeThis());
				//预收账款
				double advCustomers = Double.parseDouble(balSheet.getAdvCustomers());
				
				if (busiIncThis ==0) continue;
				
				String advanceAcctBusiRatio = MathUtils.format2DecPoint(advCustomers / busiIncThis  * 100);
						
				yearBuilder.append(incstSheet.getYear()).append(",");
				dataBuilder.append(advanceAcctBusiRatio).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("预收/营业收入(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//应付/存货
	public String dueAcctStock() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(balIn);
		
		Chart chart = new Chart();
		
		if ( balSheetsList != null && balSheetsList.size() >0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balSheetsList.size();  i++) {
				BalanceSheet  balSheet = balSheetsList.get(i);
				
				//应付账款
				double accPayable = Double.parseDouble(balSheet.getAccPayable());
				
				double goodsEnd = Double.parseDouble(balSheet.getGoodsEnd());
				
				if (goodsEnd == 0) continue;
				
				String dueAcctStock = MathUtils.format2DecPoint(accPayable / goodsEnd  * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(dueAcctStock).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("应付/存货(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//存货 / 流动资产
	public String stockLiquid() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(balIn);
		
		Chart chart = new Chart();
		
		if ( balSheetsList != null && balSheetsList.size() >0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balSheetsList.size();  i++) {
				BalanceSheet  balSheet = balSheetsList.get(i);
				
				//期末流动资产
				double liquidAssetsEnd = Double.parseDouble(balSheet.getLiquidAssetsEnd());
				
				double goodsEnd = Double.parseDouble(balSheet.getGoodsEnd());
				
				if (liquidAssetsEnd == 0) continue;
				
				String stockLiquid = MathUtils.format2DecPoint(goodsEnd / liquidAssetsEnd  * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(stockLiquid).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("存货/流动资产(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//固定资产 + 在建工程
	public String fixedAndunderCons() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(balIn);
		
		Chart chart = new Chart();
		
		if ( balSheetsList != null && balSheetsList.size() >0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balSheetsList.size();  i++) {
				BalanceSheet  balSheet = balSheetsList.get(i);
				
				//在建工程
				double constrInPro = Double.parseDouble(balSheet.getConstrInPro());
				//期末固定资产
				double fixedAssetsEnd = Double.parseDouble(balSheet.getFixedAssetsEnd());
				
				String fixedAndunderCons = MathUtils.format2DecPoint(constrInPro + fixedAssetsEnd);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(fixedAndunderCons).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("固定资产 + 在建工程");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//(固定资产 + 在建工程)占净资产比重
	public String fixedConsNetAsset() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		String balCompxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] balIn = jedis.get(balCompxKey.getBytes());
		List<BalanceSheet> balSheetsList = ObjectsTranscoder.deserialize(balIn);
		
		Chart chart = new Chart();
		
		if ( balSheetsList != null && balSheetsList.size() >0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balSheetsList.size();  i++) {
				BalanceSheet  balSheet = balSheetsList.get(i);
				
				//在建工程
				double constrInPro = Double.parseDouble(balSheet.getConstrInPro());
				//期末固定资产
				double fixedAssetsEnd = Double.parseDouble(balSheet.getFixedAssetsEnd());
				
				
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				//期末负债总额
				double totalLiabEnd= Double.parseDouble(balSheet.getTotalLiabEnd());
				
				//期末净资产
				double endNetassgrrt = totalAssEnd - totalLiabEnd;
				
				if (endNetassgrrt == 0) continue;

				String fixedConsNetAsset = MathUtils.format2DecPoint((constrInPro + fixedAssetsEnd) / endNetassgrrt * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(fixedConsNetAsset).append(",");
			}
			
			if (yearBuilder.length() > 1 && dataBuilder.length() > 1) {
				yearBuilder.deleteCharAt(yearBuilder.length() -1 );
				dataBuilder.deleteCharAt(dataBuilder.length() -1);				
			}
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("(固定资产 + 在建工程)占净资产比重(%)");
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
