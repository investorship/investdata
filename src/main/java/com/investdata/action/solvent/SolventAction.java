package com.investdata.action.solvent;

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
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 偿债能力各指标计算
 * @author HaiLong.Guo
 * @since 2016-07-11
 *
 */
public class SolventAction extends BaseAction implements RequestAware,ApplicationAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(SolventAction.class);
	private Map<String, Object> request;
	private Map<String,Object> application = null;
	private String code;
	private String indexName;
	private static Jedis jedis = RedisCache.getJedis();
	
	//流动比率
	public String currt() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		String compxKey = code + "#" + Const.BALANCEDATA_KEY;
		byte[] in = jedis.get(compxKey.getBytes());
		List<BalanceSheet> balanceSheetsList = ObjectsTranscoder.deserialize(in);  
		
		Chart chart = new Chart();
		
		//要求两张表年份数据必须一致，界面才给予显示
		if (balanceSheetsList != null && balanceSheetsList.size() > 0) {
			//保留两位小数
			StringBuilder dataBuilder = new StringBuilder();
			StringBuilder yearBuilder = new StringBuilder();
			
			for (int i=0; i<balanceSheetsList.size(); i++) {
				BalanceSheet balanceSheet = balanceSheetsList.get(i);
				
				//期末流动资产
				double  liquidAssetsEnd = Double.valueOf(balanceSheet.getLiquidAssetsEnd());
				//流动负债
				double curr_liab = Double.valueOf(balanceSheet.getCurrLiab());
				
				//流动比率= 流动资产 / 流动负债
				String currt = MathUtils.format2DecPoint(liquidAssetsEnd / curr_liab);
				
				yearBuilder.append(balanceSheet.getYear()).append(",");
				dataBuilder.append(currt).append(",");
			}
			
			yearBuilder.deleteCharAt(yearBuilder.length() -1 );
			dataBuilder.deleteCharAt(dataBuilder.length() -1);
			
			Map<String,String> stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
			String stockName = stockCodeMapping.get(code);
			
			chart.setxAxis(yearBuilder.toString());
			chart.setData(dataBuilder.toString());
			chart.setLegendData("流动比率");
			chart.setText(code + " " + stockName);
		}
		
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
