package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TFinanceIndexInfoDao;
import com.investdata.dao.po.FinanceIndexInfo;

public class FinanceIndexInfoDaoImpl extends BaseDao implements TFinanceIndexInfoDao {

	public List<FinanceIndexInfo> getParentFinanceIndexInfo(Integer pid) throws Exception {
		
		return dao.queryForList("financeIndexInfo.getParentFinanceIndexInfo", pid);
	}


}