package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.GendataSheet;


public interface TGendataSheetDao {
	public List<GendataSheet> getGendataSheet(GendataSheet gs) throws Exception;
	
}
