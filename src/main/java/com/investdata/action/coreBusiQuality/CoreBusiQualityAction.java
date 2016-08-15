package com.investdata.action.coreBusiQuality;

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
 * 核心业务质量计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */

public class CoreBusiQualityAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(CoreBusiQualityAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	private static Jedis jedis = RedisCache.getJedis();
	
	
	
	//投资收益 / 利润总额
	public String investProfitRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				
				//投资收益
				double investIncome = Double.parseDouble(incsSheet.getInvestIncome());
				//利润总额
				double totalProfitEnd= Double.parseDouble(incsSheet.getTotalProfitEnd());
				
				//投资收益 / 利润总额  * 100
				String investProfitRatio = MathUtils.format2DecPoint(investIncome / totalProfitEnd  * 100);
						
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(investProfitRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("投资收益 / 利润总额 (%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//公允价值变动 / 利润总额
	public String fairValueRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				
				//公允价值变动
				double fairValChange = Double.parseDouble(incsSheet.getFairValChange());
				//利润总额
				double totalProfitEnd= Double.parseDouble(incsSheet.getTotalProfitEnd());
				
				//公允价值变动 / 利润总额  * 100
				String fairValueRatio = MathUtils.format2DecPoint(fairValChange / totalProfitEnd  * 100);
						
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(fairValueRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("公允价值变动 / 利润总额 (%)");
			chart.setText(code + " " + stockName);
		}
		
		request.put("chart", chart);
		
		return methodName;
	}
	
	//营业外收支净额 / 利润总额
	public String noNBusiProRatio() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		String incCompxKey = code + "#" + Const.INCSTATEDATA_KEY;
		byte[] in = jedis.get(incCompxKey.getBytes());
		List<IncstateSheet> incstateSheetList = ObjectsTranscoder.deserialize(in);  		
		
		Chart chart = new Chart();
		
		if (incstateSheetList != null && incstateSheetList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<incstateSheetList.size();  i++) {
				IncstateSheet incsSheet = incstateSheetList.get(i);
				
				//营业外收入
				double nonOperaIncome = Double.parseDouble(incsSheet.getNonOperaIncome());
				//营业外支出
				double nonOperaOutcome = Double.parseDouble(incsSheet.getNonOperaOutcome());
				
				//营业外收支净额
				double noNBusiPro =  (nonOperaIncome - nonOperaOutcome);
				
				//利润总额
				double totalProfitEnd= Double.parseDouble(incsSheet.getTotalProfitEnd());
				
				//营业外收支净额  / 利润总额
				String noNBusiProRatio = MathUtils.format2DecPoint(noNBusiPro / totalProfitEnd  * 100);
						
				yearBuilder.append(incsSheet.getYear()).append(",");
				dataBuilder.append(noNBusiProRatio).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("营业外收支净额 / 利润总额 (%)");
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
