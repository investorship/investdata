package com.investdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.investdata.dao.TFinanceIndexInfoDao;
import com.investdata.dao.po.AdminUser;
import com.investdata.dao.po.FinanceIndexInfo;

public class FinanceIndexInfoDaoImpl extends BaseDao implements TFinanceIndexInfoDao {

	public List<FinanceIndexInfo> getFinanceIndexInfos(FinanceIndexInfo finIndexInfo) throws Exception {
		
		return dao.queryForList("financeIndexInfo.getFinanceIndexInfos", finIndexInfo);
	}

	@Override
	public int getTotalCount() throws Exception {
		return (Integer)dao.queryForObject("financeIndexInfo.getTotalCount");
	}

	@Override
	public List<FinanceIndexInfo> findFinanceIndexInfosByPage(Map map) throws Exception {
		return dao.queryForList("financeIndexInfo.FindByPage", map);
	}


}