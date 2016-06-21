package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.IndustryCategory;


public interface TIndustryCategoryDao {
	public List<IndustryCategory> getIndustryCategorys(IndustryCategory ic) throws Exception;
	
}
