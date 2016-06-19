package com.investdata.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	public static final String SUCCESS = "success";
	public static final String INPUT = "input";
	public static final String RESET_PWD_INPUT = "reset_pwd_input";
	public static final String UPDATE_PWD_INPUT = "update_pwd_input";
	public static final String UPDATE_PWD_SUCC = "update_pwd_succ";
	public static final String UPDATE_PWD_NOLOGIN_FAIL = "update_pwd_nologin_fail";
	public static final String RESET_PWD_SUCC = "reset_pwd_succ";
	public static final String RESET_PWD_MAIL = "reset_pwd_mail";
	public static final String FAIL = "fail";
	public static final String LOGIN_SUCC = "login_succ";
	public static final String LOGIN_FAIL = "login_fail";
	public static final String LOGOUT = "logout";
	public static final String ADMINLOGOUT = "adminLogout";
	public static final String REG_SUCC = "reg_succ";
	public static final String AJAX = "ajax";
	public static final String AJAX_ADMIN = "ajax_admin";
	public static final String ACTIVE_FAIL = "active_fail";
	public static final String ACTIVE_SUCC = "active_succ";
	
	public static final String UPDATE_PWD_OPERATION_FLAG = "1";
	public static final String RESET_PWD_OPERATION_FLAG = "2";
	
	/**
	 * 公用往前台打印JSON数据
	 * @param jsonObject
	 */
	public void sendOutMsg(JSONObject jsonObject) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json, charset=utf-8");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(jsonObject);
		out.close();
	}
	
}
