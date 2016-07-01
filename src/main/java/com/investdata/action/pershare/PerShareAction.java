package com.investdata.action.pershare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;

import redis.clients.jedis.Jedis;

import com.investdata.common.BaseAction;
import com.investdata.common.Const;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TGendataSheetDao;
import com.investdata.dao.TIncstateSheetDao;
import com.investdata.dao.po.Chart;
import com.investdata.dao.po.GendataSheet;
import com.investdata.dao.po.IncstateSheet;
import com.investdata.redis.ObjectsTranscoder;
import com.investdata.redis.RedisCache;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;

public class PerShareAction extends BaseAction implements RequestAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(PerShareAction.class);
	private Map<String, Object> request;
	private String code;
	private Jedis jedis = RedisCache.getJedis();
	
	//每股收益
	public String EPS() throws Exception {
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
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
		
		//封装图标信息
		Chart chart = new Chart();
		chart.setxAxis(yearData.toString());
	
		List<String> dataList = new ArrayList<String>();
		dataList.add(epsData.toString());
		chart.setDataList(dataList);
		request.put("chart", chart);
		String methodName = (String)ActionContext.getContext().get("methodName");
		
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
		
		StringBuilder epsData = new StringBuilder();
		StringBuilder yearData = new StringBuilder();
		
		for (GendataSheet gs : genDataList) {
			epsData.append(gs.getEpsDiluted()).append(","); //取稀释每股收益值
			yearData.append(gs.getYear()).append(",");
		}
		
		//去掉最后一个逗号
		if (genDataList != null && genDataList.size() > 1) {
			epsData.deleteCharAt(epsData.length() -1);
			yearData.deleteCharAt(yearData.length() -1);			
		}
		
		Chart chart = new Chart();
		chart.setxAxis(yearData.toString());
	
		List<String> dataList = new ArrayList<String>();
		dataList.add(epsData.toString());
		chart.setDataList(dataList);
		
		request.put("chart", chart);
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		return methodName;
	}
	
	//每股营业收入
	public String mincmPS() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		TIncstateSheetDao isd =  DaoFactory.getTIncstateSheetDao();
		IncstateSheet is = new IncstateSheet();
		is.setCode(code);
		
//		List<IncstateSheet> inSheetList =  isd.getIncstateSheet(is);
		
		
		
		
		
		
		return methodName;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
