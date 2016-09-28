package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TCashFlowSheetDao;
import com.investdata.dao.po.CashFlowSheet;

public class CashFlowSheetDaoImpl extends BaseDao implements TCashFlowSheetDao {
	
	@Override
	public List<CashFlowSheet> getCashFlowSheets(CashFlowSheet cs) throws Exception {
		return dao.queryForList("cashFlowSheet.getCashFlowSheets", cs);
	}

	@Override
	public void addCashFlowSheet(CashFlowSheet cs) throws Exception {
		dao.insert("cashFlowSheet.addCashFlowSheet", cs);
	}

	@Override
	public void updateCashFlowSheet(CashFlowSheet cs) throws Exception {
		dao.update("cashFlowSheet.updateCashFlowSheet", cs);
	}

	

}