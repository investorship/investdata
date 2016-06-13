package com.investdata.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONArray;
import org.json.JSONObject;

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
	private JSONObject jsonUser;
	
	public String execute() throws Exception {
		return INPUT;
	}
	
	/**
	 * 分页查询
	 * json格式如下：
	 * {
	 *		"draw":1,
	 *		"recordsTotal":1,
	 *		"recordsFiltered":1,
	 *		"data":"
	 *					[
	 *						{
	 *							"flag":0,
	 *							"inTime":"2016-06-05 19:20:02.0",
	 *							"email":"developerli@126.com",
	 *							"isPayer":0,
	 *							"userName":"hahalzb",
	 *							"activeCode":"d8cd730be8edcb785f51e11cdf379c95",
	 *							"password":"DC16Pe+/vT/vv73vv70="
	 *						}
	 *					]
	 *	}
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		HttpServletRequest httpRequest = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json, charset=utf-8");
		
		int start = Integer.parseInt(StringUtils.trim(httpRequest.getParameter("start")));
		int length = Integer.parseInt(StringUtils.trim(httpRequest.getParameter("length")));
		int draw = Integer.parseInt(StringUtils.trim(httpRequest.getParameter("draw")));
		
		TUserDao userDao = DaoFactory.getTUserDao();
		int recordsTotal = userDao.getTotalCount();
		int recordsFiltered = recordsTotal;
		
		jsonUser = new JSONObject();
		jsonUser.put("draw", draw);
		jsonUser.put("recordsTotal", recordsTotal);
		jsonUser.put("recordsFiltered", recordsFiltered);
		
		Map qryMap = new HashMap();
		qryMap.put("start", start);//起始记录
		qryMap.put("length", length);//每页展示条数
		List<User> userList = userDao.getUsers(qryMap);
		
		List<JSONObject> userJsonList = new ArrayList<JSONObject>();
		for(User user : userList) {
			userJsonList.add(user.toJson());
		}
		JSONArray jsonArray = new JSONArray(userJsonList); 
		jsonUser.put("data", jsonArray);
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(jsonUser);
		
		out.close();
		
		return AJAX;
	}
	
	public String makeUpJsonStr() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"draw\":1,\"recordsTotal\":1,\"recordsFiltered\":1,\"data\":[{\"flag\":0,\"inTime\":\"2016-06-05 19:20:02.0\",\"email\":\"developerli@126.com\",\"isPayer\":0,\"userName\":\"hahalzb\",\"activeCode\":\"d8cd730be8edcb785f51e11cdf379c95\",\"password\":\"DC16Pe+/vT/vv73vv70=\"}]}");
		return sb.toString();
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setJsonUser(JSONObject jsonUser) {
		this.jsonUser = jsonUser;
	}

}
