package com.investdata.dao;

import java.util.List;
import java.util.Map;

import com.investdata.dao.po.IncstateSheet;


public interface TIncstateSheetDao {
	
	public int getTotalCount(Map map) throws Exception;
	
	public List<IncstateSheet> findIncstateSheetInfosByPage(Map map) throws Exception;
	
	public List<IncstateSheet> getIncstateSheets(IncstateSheet is) throws Exception;
	
	public void addIncstateSheet(IncstateSheet is) throws Exception;
	
	public void updateIncstateSheet(IncstateSheet is) throws Exception;
}
