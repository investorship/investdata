package com.investdata.dao;

import java.util.List;
import java.util.Map;

import com.investdata.dao.po.GendataSheet;


public interface TGendataSheetDao {
	
	public int getTotalCount(Map map) throws Exception;
	
	public List<GendataSheet> findGendataSheetInfosByPage(Map map) throws Exception;
	
	public List<GendataSheet> getGendataSheets(GendataSheet gs) throws Exception;
	
	public void addGendataSheet(GendataSheet gs) throws Exception;
	
	public void updateGendataSheet(GendataSheet gs) throws Exception;
	
}
