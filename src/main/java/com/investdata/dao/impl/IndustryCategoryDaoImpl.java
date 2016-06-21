package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TIndustryCategoryDao;
import com.investdata.dao.po.IndustryCategory;

public class IndustryCategoryDaoImpl extends BaseDao implements TIndustryCategoryDao {

	@Override
	public List<IndustryCategory> getIndustryCategorys(IndustryCategory ic) throws Exception {
		return dao.queryForList("industryCategory.getIndustryCategorys", ic);
	}

	

}