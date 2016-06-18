package com.investdata.dao;

import java.util.List;
import java.util.Map;

import com.investdata.dao.po.AdminUser;
import com.investdata.dao.po.FinanceIndexInfo;

public interface TFinanceIndexInfoDao {
	public int getTotalCount() throws Exception;
	public List<FinanceIndexInfo> findFinanceIndexInfosByPage(Map map) throws Exception;
	public List<FinanceIndexInfo> getFinanceIndexInfos(FinanceIndexInfo finIndexInfo) throws Exception;
	
}
