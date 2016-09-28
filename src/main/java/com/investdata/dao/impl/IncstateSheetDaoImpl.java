package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TIncstateSheetDao;
import com.investdata.dao.po.IncstateSheet;

public class IncstateSheetDaoImpl extends BaseDao implements TIncstateSheetDao {

	@Override
	public List<IncstateSheet> getIncstateSheets(IncstateSheet is) throws Exception {
		return dao.queryForList("incstateSheet.getIncstateSheets", is);
	}

	@Override
	public void addIncstateSheet(IncstateSheet is) throws Exception {
		dao.insert("incstateSheet.addIncstateSheet", is);
	}

	@Override
	public void updateIncstateSheet(IncstateSheet is) throws Exception {
		dao.update("incstateSheet.updateIncstateSheet", is);
	}

	

}