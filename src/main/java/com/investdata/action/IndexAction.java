package com.investdata.action;

import org.apache.log4j.Logger;

import com.investdata.common.BaseAction;

/**
 * 跳转到首页Action
 */
public class IndexAction extends BaseAction {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger logger = Logger.getLogger(IndexAction.class);	
	
	public String execute() throws Exception {
		System.err.println("进入首页Action,可以加载股票数据！");
		return INPUT;
	}
}
