package com.investdata.action.capitalEnsemble;

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
 * 资本结构计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */

public class CapitalEnsembleAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(CapitalEnsembleAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	private static Jedis jedis = RedisCache.getJedis();
	
	
	
	//资产负债率
	public String dbastrt() throws Exception {
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
				
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				//期末负债总额
				double totalLiabEnd= Double.parseDouble(balSheet.getTotalLiabEnd());
				
				//资产负债率 = 负债总额 /资产总额  * 100%
				String dbastrt = MathUtils.format2DecPoint(totalLiabEnd / totalAssEnd  * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(dbastrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("资产负债率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//权益乘数
	public String equmul() throws Exception {
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
				
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				//期末股东权益总额
				double shareHolderEnd= Double.parseDouble(balSheet.getShareHolderEnd());
				
				//权益乘数 = 期末资产总额 /期末股东权益总额  * 100%
				String equmul = MathUtils.format2DecPoint(totalAssEnd / shareHolderEnd);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(equmul).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("权益乘数");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//固定资产比率
	public String fixassrt() throws Exception {
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
				
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				//期末固定资产
				double fixedAssetsEnd= Double.parseDouble(balSheet.getFixedAssetsEnd());
				
				//固定资产比率 = 期末固定资产 /期末资产总额  * 100%
				String fixassrt = MathUtils.format2DecPoint(fixedAssetsEnd / totalAssEnd * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(fixassrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("固定资产比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	
	//无形资产比率
	public String intanassrt() throws Exception {
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
				
				//资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				//无形资产
				double lntangAssets= Double.parseDouble(balSheet.getLntangAssets());
				
				//无形资产比率 = 无形资产 /期末资产总额  * 100%
				String intanassrt = MathUtils.format2DecPoint(lntangAssets / totalAssEnd * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(intanassrt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("无形资产比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//商誉占净资产比例
	public String goodwill() throws Exception {
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
				
				
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				//期末负债总额
				double totalLiabEnd= Double.parseDouble(balSheet.getTotalLiabEnd());
				
				//期末净资产
				double endNetassgrrt = totalAssEnd - totalLiabEnd;
				
				//商誉
				double goodwill= Double.parseDouble(balSheet.getGoodWill());
				
				//商誉占净资产比例 = 商誉 /净资产(资产总额 -负债总额)  * 100%
				String goodwillRatio = MathUtils.format2DecPoint(goodwill / endNetassgrrt * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(goodwillRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("商誉占净资产比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//股东权益比率
	public String equass() throws Exception {
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
				
				
				//期末资产总额
				double totalAssEnd = Double.parseDouble(balSheet.getTotalAssEnd());
				//期末股东权益
				double shareHolderEnd= Double.parseDouble(balSheet.getShareHolderEnd());
				
				//股东权益比率= 股东权益 / 资产总额  * 100%
				String equass = MathUtils.format2DecPoint(shareHolderEnd / totalAssEnd * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(equass).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("股东权益比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//负债结构比率
	public String debtEnsemble() throws Exception {
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
				
				//流动负债
				double currLiab = Double.parseDouble(balSheet.getCurrLiab());
				
				//长期借款
				double longTermLoans= Double.parseDouble(balSheet.getLongTermLoans());
				
				//应付债券
				double boundsPayAable= Double.parseDouble(balSheet.getBoundsPayable());
				
				//长期应付款
				double longAccPayable= Double.parseDouble(balSheet.getLongAccPayable());
				
				double total = longTermLoans + boundsPayAable + longAccPayable;
				
				if (total == 0) continue;
				
				//负债结构比率= 流动负债 / 长期负债（长期负债=长期借款  + 应付债券 + 长期应付款） * 100%
				String debtEnsemble = MathUtils.format2DecPoint((currLiab / total) * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(debtEnsemble).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("负债结构比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//长期负债权益比率
	public String longDebtRatio() throws Exception {
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
				
				//期末股东权益
				double shareHolderEnd = Double.parseDouble(balSheet.getShareHolderEnd());
				
				//长期借款
				double longTermLoans= Double.parseDouble(balSheet.getLongTermLoans());
				
				//应付债券
				double boundsPayAable= Double.parseDouble(balSheet.getBoundsPayable());
				
				//长期应付款
				double longAccPayable= Double.parseDouble(balSheet.getLongAccPayable());
				
				//长期负债权益比率= 长期负债（长期负债=长期借款  + 应付债券 + 长期应付款）/所有者权益  * 100%
				String longDebtRatio = MathUtils.format2DecPoint((longTermLoans + boundsPayAable + longAccPayable) / shareHolderEnd * 100);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(longDebtRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("长期负债权益比率(%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//营运资金
	public String wrkcap() throws Exception {
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
				
				//流动负债
				double currLiab = Double.parseDouble(balSheet.getCurrLiab());
				
				//期末流动资产
				double longAccPayable= Double.parseDouble(balSheet.getLiquidAssetsEnd());
				
				//营运资金 = 流动资产 - 流动负债
				String wrkcap = MathUtils.format2DecPoint(currLiab - longAccPayable);
						
				yearBuilder.append(balSheet.getYear()).append(",");
				dataBuilder.append(wrkcap).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("营运资金");
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
