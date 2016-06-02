package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.FinanceIndexInfo;

public interface TFinanceIndexInfoDao {
	public List<FinanceIndexInfo> getParentFinanceIndexInfo(Integer pid) throws Exception;
	
}
