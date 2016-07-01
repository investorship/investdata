package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TIncstateSheetDao;
import com.investdata.dao.po.IncstateSheet;

public class IncstateSheetDaoImpl extends BaseDao implements TIncstateSheetDao {

	@Override
	public List<IncstateSheet> getIncstateSheets(IncstateSheet is) throws Exception {
		return dao.queryForList("incstateSheet.getIncstateSheet", is);
	}

	

}