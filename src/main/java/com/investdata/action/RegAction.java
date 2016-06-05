package com.investdata.action;

import org.apache.log4j.Logger;

import com.investdata.common.BaseAction;

/**
 * 注册Action
 */
public class RegAction extends BaseAction {
	private static final long serialVersionUID = -4003526420872337090L;
	Logger logger = Logger.getLogger(RegAction.class);

	public String execute() throws Exception {
		logger.info("进入注册流程..");
		return INPUT;
	}

}
