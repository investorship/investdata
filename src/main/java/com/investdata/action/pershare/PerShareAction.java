package com.investdata.action.pershare;

import org.apache.log4j.Logger;

import com.investdata.common.BaseAction;

public class PerShareAction extends BaseAction {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(PerShareAction.class);
	
	public String EPS() throws Exception {
		return "eps";
	}
}
