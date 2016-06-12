package com.investdata.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.User;
import com.investdata.utils.StringUtils;

/**
 * 注册用户Action
 */
public class UserMgrAction extends BaseAction implements RequestAware,SessionAware {
	Logger _log = Logger.getLogger(UserMgrAction.class);
	private Map<String,Object> request;
	private Map<String,Object> session;
	
	public String execute() throws Exception {
		return INPUT;
	}
	
	public String query() throws Exception {
		HttpServletRequest httpRequest = ServletActionContext.getRequest();
		int start = Integer.parseInt(StringUtils.trim(httpRequest.getParameter("start")));
		int length = Integer.parseInt(StringUtils.trim(httpRequest.getParameter("length")));
		Map qryMap = new HashMap();
		qryMap.put("start", start);
		qryMap.put("length", length);
		
		TUserDao userDao = DaoFactory.getTUserDao();
		List<User> useList = userDao.getUsers(qryMap);
		
		
		
		
		return AJAX;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
}
