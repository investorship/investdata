package com.investdata.action;

import org.apache.log4j.Logger;

import com.investdata.common.BaseAction;

/**
 * 股票相关Action
 */
public class StockAction extends BaseAction {
	private static final long serialVersionUID = -4003526420872337090L;
	Logger logger = Logger.getLogger(StockAction.class);

	public String execute() throws Exception {
		logger.info("进入股票明细流程..");
		return INPUT;
	}

}
