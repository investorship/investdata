package com.investdata.dao;

import java.util.List;
import java.util.Map;

import com.investdata.dao.po.BalanceSheet;


public interface TBalanceSheetDao {
	
	public int getTotalCount(Map map) throws Exception;
	
	public List<BalanceSheet> findBalanceSheetInfosByPage(Map map) throws Exception;
	
	public List<BalanceSheet> getBalanceSheets(BalanceSheet bs) throws Exception;
	
	public void addBalanceSheet(BalanceSheet bs) throws Exception;
	
	public void updateBalanceSheet(BalanceSheet bs) throws Exception;
}
