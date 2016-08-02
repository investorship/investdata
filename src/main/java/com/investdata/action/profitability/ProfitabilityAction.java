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
