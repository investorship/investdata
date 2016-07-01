package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TGendataSheetDao;
import com.investdata.dao.po.GendataSheet;

public class GendataSheetDaoImpl extends BaseDao implements TGendataSheetDao {

	@Override
	public List<GendataSheet> getGendataSheets(GendataSheet gs) throws Exception {
		return dao.queryForList("gendataSheet.getGendataSheet", gs);
	}

	

}