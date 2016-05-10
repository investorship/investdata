package com.investdata.action;

import org.apache.log4j.Logger;

import com.investdata.common.BaseAction;

/**
 * 登录Action
 */
public class LoginAction extends BaseAction {
	Logger logger = Logger.getLogger(LoginAction.class);
	
	public static final String success = "success";
	
	public String login() {
		logger.info("olosjfjsdfjksjfdjf");
		System.err.println("012901201291930213");
		return success;
	}
	
}
