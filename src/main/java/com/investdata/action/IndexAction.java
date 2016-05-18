package com.investdata.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.investdata.common.BaseAction;

/**
 * 跳转到首页Action
 */
public class IndexAction extends BaseAction implements RequestAware, SessionAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger logger = Logger.getLogger(IndexAction.class);
	private static StringBuilder sBuilder = new StringBuilder();
	private Map<String,Object> request = null;
	
	public String execute() throws Exception {
		if (sBuilder.length() == 0) {
			sBuilder.append("[\"HLMD    600256    海立美达    A股    深圳\"").append(",").append("\n");
			sBuilder.append("\"SHFZ    600254    双汇发展    A股    上海\"]");			
		}
		
		request.put("stockData", sBuilder.toString());
		return INPUT;
	}

	public void setSession(Map<String, Object> session) {
		
		
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
		
	}
	
	public static void main(String[] args) {
		StringBuilder s1 = new StringBuilder();
		s1.append("[\"HLMD   600256	 海立美达      A股      深圳\"").append(",").append("\n");
		s1.append("\"SHFZ   600254	 双汇发展      A股      上海\"]");	
		System.err.println(s1);
	}
}
