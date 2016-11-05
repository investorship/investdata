package com.investdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.investdata.dao.TGendataSheetDao;
import com.investdata.dao.po.GendataSheet;

public class GendataSheetDaoImpl extends BaseDao implements TGendataSheetDao {

	@Override
	public List<GendataSheet> getGendataSheets(GendataSheet gs) throws Exception {
		return dao.queryForList("gendataSheet.getGendataSheets", gs);
	}

	@Override
	public void addGendataSheet(GendataSheet gs) throws Exception {
		dao.insert("gendataSheet.addGendata", gs);
	}

	@Override
	public void updateGendataSheet(GendataSheet gs) throws Exception {
		dao.update("gendataSheet.updateGendata", gs);
	}

	@Override
	public int getTotalCount(Map map) throws Exception {
		return (Integer)dao.queryForObject("gendataSheet.getTotalCount",map);
	}

	@Override
	public List<GendataSheet> findGendataSheetInfosByPage(Map map) throws Exception {
		return dao.queryForList("gendataSheet.FindByPage", map);
	}

	

}