package com.investdata.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.json.JSONObject;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TAdminUserDao;
import com.investdata.dao.po.AdminUser;

/**
 * 后台管理员用户管理
 */
public class AdminUserMgrAction extends BaseAction  implements RequestAware{
	private static final long serialVersionUID = -4003526420872337090L;
	Logger logger = Logger.getLogger(AdminUserMgrAction.class);
	private String jsonAjaxDataRows;
	private String jsonData;
	public String getJsonData() {
		return jsonData;
	}

	private Map<String, Object> request;

	
	public String execute() throws Exception {
		return INPUT;
	}
	
	public String query() throws Exception {
		TAdminUserDao adminUserDao = DaoFactory.getTAdminUserDao();
		List<AdminUser> adminUsers = adminUserDao.getAdminUsers(new AdminUser());
		List rows = new ArrayList();
		for (int i=0; i< adminUsers.size(); i++) {
			AdminUser adUser = adminUsers.get(i);
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("userName", adUser.getUserName());
			jsonUser.put("email", adUser.getEmail());
			jsonUser.put("perm_level", adUser.getPermLevel());
			jsonUser.put("flag", adUser.getFlag());
			jsonUser.put("intime", adUser.getInTime());
			rows.add(i,jsonUser);
		}
		logger.debug(String.format("rows=[%s]", rows));
		
		JSONObject test = new JSONObject();
		test.put("page", "1");
		test.put("total", "1");
		test.put("records", adminUsers.size());
		test.put("rows", rows);
		
		jsonData = test.toString();
		
		return AJAX;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

}
