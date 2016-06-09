package com.investdata.action;

import org.apache.log4j.Logger;

import com.investdata.common.BaseAction;

/**
 * 重设密码
 */
public class ResetPwdAction extends BaseAction {
	private static final long serialVersionUID = -4003526420872337090L;
	Logger logger = Logger.getLogger(ResetPwdAction.class);

	public String execute() throws Exception {
		logger.info("进入重设登录密码流程");
		return INPUT;
	}
	
	public String sendResetMail() throws Exception{
		
		return INPUT;
	}

}
