package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TBalanceSheetDao;
import com.investdata.dao.po.BalanceSheet;

public class BalanceSheetDaoImpl extends BaseDao implements TBalanceSheetDao {

	@Override
	public List<BalanceSheet> getBalanceSheets(BalanceSheet bs) throws Exception {
		return dao.queryForList("balanceSheet.getBalanceSheets", bs);
	}
	
}