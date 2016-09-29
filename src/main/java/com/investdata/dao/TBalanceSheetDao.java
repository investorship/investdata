package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.BalanceSheet;


public interface TBalanceSheetDao {
	public List<BalanceSheet> getBalanceSheets(BalanceSheet bs) throws Exception;
	
	public void addBalanceSheet(BalanceSheet bs) throws Exception;
	
	public void updateBalanceSheet(BalanceSheet bs) throws Exception;
}
