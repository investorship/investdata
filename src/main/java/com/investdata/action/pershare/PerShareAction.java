package com.investdata.action.pershare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TGendataSheetDao;
import com.investdata.dao.TIncstateSheetDao;
import com.investdata.dao.po.Chart;
import com.investdata.dao.po.GendataSheet;
import com.investdata.dao.po.IncstateSheet;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;

public class PerShareAction extends BaseAction implements RequestAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(PerShareAction.class);
	private Map<String, Object> request;
	private String code;
	
	//每股收益
	public String EPS() throws Exception {
		if (StringUtils.isEmpty(code)) {
			return ERROR;
		}
		
		TGendataSheetDao genSheetDao = DaoFactory.getTGendataSheetDao();
		GendataSheet genSheet = new GendataSheet();
		genSheet.setCode(code);
		List<GendataSheet> genDataList = genSheetDao.getGendataSheet(genSheet);
		
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
		
		TGendataSheetDao genSheetDao = DaoFactory.getTGendataSheetDao();
		GendataSheet genSheet = new GendataSheet();
		genSheet.setCode(code);
		List<GendataSheet> genDataList = genSheetDao.getGendataSheet(genSheet);
		
		StringBuilder epsData = new StringBuilder();
		StringBuilder yearData = new StringBuilder();
		
		for (GendataSheet gs : genDataList) {
			epsData.append(gs.getEpsDiluted()).append(",");
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
		
		List<IncstateSheet> inSheetList =  isd.getIncstateSheet(is);
		
		
		
		
		
		
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
