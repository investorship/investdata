package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.IncstateSheet;


public interface TIncstateSheetDao {
	public List<IncstateSheet> getIncstateSheets(IncstateSheet is) throws Exception;
	
	public void addIncstateSheet(IncstateSheet is) throws Exception;
	
	public void updateIncstateSheet(IncstateSheet is) throws Exception;
}
