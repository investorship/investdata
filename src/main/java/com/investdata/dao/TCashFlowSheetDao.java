package com.investdata.dao;

import java.util.List;
import java.util.Map;

import com.investdata.dao.po.CashFlowSheet;


public interface TCashFlowSheetDao {
	
	public int getTotalCount(Map map) throws Exception;
	
	public List<CashFlowSheet> findCashflowSheetInfosByPage(Map map) throws Exception;
	
	public List<CashFlowSheet> getCashFlowSheets(CashFlowSheet cs) throws Exception;
	
	public void addCashFlowSheet(CashFlowSheet cs) throws Exception;
	
	public void updateCashFlowSheet(CashFlowSheet cs) throws Exception;
	
}
