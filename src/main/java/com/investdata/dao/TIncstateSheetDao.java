package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.IncstateSheet;


public interface TIncstateSheetDao {
	public List<IncstateSheet> getIncstateSheet(IncstateSheet is) throws Exception;
	
}
