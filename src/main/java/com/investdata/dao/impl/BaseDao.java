package com.investdata.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.investdata.common.factory.SqlMapClientFactory;

public class BaseDao {
	protected SqlMapClient dao = null;
	
	protected BaseDao() {
		this.dao = SqlMapClientFactory.getInstance();
	}
	
}
