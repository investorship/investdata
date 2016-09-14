package com.investdata.action.admin;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;

import com.investdata.common.BaseAction;
import com.investdata.redis.CacheManager;

/**
 * 后台管理-缓存重载管理
 */
public class CacheMgrAction extends BaseAction implements RequestAware {
	
	Logger _log = Logger.getLogger(UserMgrAction.class);
	private Map<String,Object> request;
	


	public String execute() throws Exception {
		return INPUT;
	}
	
	//重载股票列表(搜索指数)
	public String relaodStockList() throws Exception {
		CacheManager.initStockData();
		request.put("funcName", "股票搜索列表");
		return INPUT;
	}
	
	//重载财务指标数据
	public String relaodFinanceIndex() throws Exception {
		CacheManager.initFinanceIndexInfo();
		request.put("funcName", "财务指标数据");
		return INPUT;
	}
	
	//重载财务数据
	public String relaodFinanceData() throws Exception {
		CacheManager.initGendataSheet();  // 初始化综合数据表项数据.
		CacheManager.initIncstateSheet(); //初始化利润表数数据
		CacheManager.initCashFlowSheet(); //初始化现金流量表数据
		CacheManager.initBalanceSheet();  //初始化资产负债表数据
		request.put("funcName", "三大财务报表)");
		return INPUT;
	}
	
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
}
