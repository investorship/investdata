package com.investdata.action;

import org.apache.log4j.Logger;

import com.investdata.common.BaseAction;

/**
 * 登录Action
 */
public class LoginAction extends BaseAction {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger logger = Logger.getLogger(LoginAction.class);	
	
	public String execute() throws Exception {
		logger.info("进入登录流程..");
		return INPUT;
	}
	
	public String login() {
		System.err.println("username=" + username + " password=" + pwd + " cap=" + captcha);
//		DaoFactory.getTUserDao();
		return INPUT;
	}
	
	/**
	 * 管理员登录
	 * @return
	 */
	public String adminLogin() {
		System.err.println("username=" + username + " password=" + pwd + " cap=" + captcha);
//		DaoFactory.getTUserDao();
		return INPUT;
	}
	
	private String username;
	private String pwd;
	private String captcha;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
