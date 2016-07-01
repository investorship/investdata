package com.investdata.common.factory;

import java.util.HashMap;
import java.util.Map;

import com.investdata.dao.TAdminUserDao;
import com.investdata.dao.TCashFlowSheetDao;
import com.investdata.dao.TFinanceIndexInfoDao;
import com.investdata.dao.TGendataSheetDao;
import com.investdata.dao.TIncstateSheetDao;
import com.investdata.dao.TIndustryCategoryDao;
import com.investdata.dao.TMgrMenuDao;
import com.investdata.dao.TStockDao;
import com.investdata.dao.TUserDao;
import com.investdata.dao.impl.AdminUserDaoImpl;
import com.investdata.dao.impl.BaseDao;
import com.investdata.dao.impl.CashFlowSheetDaoImpl;
import com.investdata.dao.impl.FinanceIndexInfoDaoImpl;
import com.investdata.dao.impl.GendataSheetDaoImpl;
import com.investdata.dao.impl.IncstateSheetDaoImpl;
import com.investdata.dao.impl.IndustryCategoryDaoImpl;
import com.investdata.dao.impl.MgrMenuDaoImpl;
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
	
	public static TAdminUserDao getTAdminUserDao() {
		String key = "TAdminUserDao";
		if (!daoMap.containsKey(key)) {
			daoMap.put(key, new AdminUserDaoImpl());
		}
		return (TAdminUserDao) daoMap.get(key);
	}
	
	public static TMgrMenuDao getTMgrMenuDao() {
		String key = "TMgrMenuDao";
		if (!daoMap.containsKey(key)) {
			daoMap.put(key, new MgrMenuDaoImpl());
		}
		return (TMgrMenuDao) daoMap.get(key);
	}
	
	public static TIndustryCategoryDao getTIndustryCategoryDao() {
		String key = "TIndustryCategoryDao";
		if (!daoMap.containsKey(key)) {
			daoMap.put(key, new IndustryCategoryDaoImpl());
		}
		return (TIndustryCategoryDao) daoMap.get(key);
	}
	
	public static TGendataSheetDao getTGendataSheetDao() {
		String key = "TGendataSheetDao";
		if (!daoMap.containsKey(key)) {
			daoMap.put(key, new GendataSheetDaoImpl());
		}
		return (TGendataSheetDao) daoMap.get(key);
	}
	
	public static TIncstateSheetDao getTIncstateSheetDao() {
		String key = "TIncstateSheetDao";
		if (!daoMap.containsKey(key)) {
			daoMap.put(key, new IncstateSheetDaoImpl());
		}
		return (TIncstateSheetDao) daoMap.get(key);
	}
	
	public static TCashFlowSheetDao getTCashFlowSheetDao() {
		String key = "TCashFlowSheetDao";
		if (!daoMap.containsKey(key)) {
			daoMap.put(key, new CashFlowSheetDaoImpl());
		}
		return (TCashFlowSheetDao) daoMap.get(key);
	}
}
