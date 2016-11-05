package com.investdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.investdata.dao.TBalanceSheetDao;
import com.investdata.dao.po.BalanceSheet;

public class BalanceSheetDaoImpl extends BaseDao implements TBalanceSheetDao {

	@Override
	public List<BalanceSheet> getBalanceSheets(BalanceSheet bs) throws Exception {
		return dao.queryForList("balanceSheet.getBalanceSheets", bs);
	}

	@Override
	public void addBalanceSheet(BalanceSheet bs) throws Exception {
		dao.insert("balanceSheet.addBalanceSheet", bs);
	}

	@Override
	public void updateBalanceSheet(BalanceSheet bs) throws Exception {
		dao.update("balanceSheet.updateBalanceSheet", bs);
	}

	@Override
	public int getTotalCount(Map map) throws Exception {
		
		return (Integer)dao.queryForObject("balanceSheet.getTotalCount", map);
	}

	@Override
	public List<BalanceSheet> findBalanceSheetInfosByPage(Map map) throws Exception {
		return dao.queryForList("balanceSheet.FindByPage", map);
	}
	
}