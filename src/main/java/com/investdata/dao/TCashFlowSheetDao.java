package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.CashFlowSheet;


public interface TCashFlowSheetDao {
	public List<CashFlowSheet> getCashFlowSheets(CashFlowSheet cs) throws Exception;
	
	public void addCashFlowSheet(CashFlowSheet cs) throws Exception;
	
	public void updateCashFlowSheet(CashFlowSheet cs) throws Exception;
	
}
