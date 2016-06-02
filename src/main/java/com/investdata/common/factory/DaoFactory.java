package com.investdata.common.factory;

import java.util.HashMap;
import java.util.Map;

import com.investdata.dao.TFinanceIndexInfoDao;
import com.investdata.dao.TStockDao;
import com.investdata.dao.TUserDao;
import com.investdata.dao.impl.BaseDao;
import com.investdata.dao.impl.FinanceIndexInfoDaoImpl;
import com.investdata.dao.impl.StockDaoImpl;
import com.investdata.dao.impl.UserDaoImpl;

public class DaoFactory {
	private static Map<String, BaseDao> daoMap = new HashMap<String, BaseDao>();

	public static TUserDao getTUserDao() {
		String key = "TUserDao";
		if (!daoMap.containsKey(key)) {
			daoMap.put(key, new UserDaoImpl());
		}
		return (TUserDao) daoMap.get(key);
	}
	
	public static TStockDao getTStockDao() {
		String key = "TStockDao";
		if (!daoMap.containsKey(key)) {
			daoMap.put(key, new StockDaoImpl());
		}
		return (TStockDao) daoMap.get(key);
	}
	
	public static TFinanceIndexInfoDao getTFinanceIndexInfoDao() {
		String key = "TFinanceIndexInfoDao";
		if (!daoMap.containsKey(key)) {
			daoMap.put(key, new FinanceIndexInfoDaoImpl());
		}
		return (TFinanceIndexInfoDao) daoMap.get(key);
	}
}
