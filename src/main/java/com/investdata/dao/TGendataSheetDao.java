package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.GendataSheet;


public interface TGendataSheetDao {
	public List<GendataSheet> getGendataSheets(GendataSheet gs) throws Exception;
	
	public void addGendataSheet(GendataSheet gs) throws Exception;
	
	public void updateGendataSheet(GendataSheet gs) throws Exception;
	
}
