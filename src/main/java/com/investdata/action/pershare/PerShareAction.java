package com.investdata.action.pershare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;

import com.investdata.common.BaseAction;
import com.investdata.dao.po.Chart;
import com.opensymphony.xwork2.ActionContext;

public class PerShareAction extends BaseAction implements RequestAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(PerShareAction.class);
	private Map<String, Object> request;
	public String EPS() throws Exception {
		Chart chart = new Chart();
		chart.setxAxis("2014,2015,2016,2017");
		String data = "1.0,2.0,3.0,4.0";
		List<String> dataList = new ArrayList<String>();
		dataList.add(data);
		chart.setDataList(dataList);
		request.put("chart", chart);
		
		String methodName = (String)ActionContext.getContext().get("methodName");
		
		return "eps";
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
}
