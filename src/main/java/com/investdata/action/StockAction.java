package com.investdata.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TStockDao;
import com.investdata.dao.po.Stock;
import com.investdata.utils.StringUtils;

/**
 * 股票相关Action
 */
public class StockAction extends BaseAction  implements RequestAware,ApplicationAware{
	private static final long serialVersionUID = -4003526420872337090L;
	private String keyword;
	Map<String, Object> request;
	Map<String, Object> application;

	Logger logger = Logger.getLogger(StockAction.class);
	
	public String execute() throws Exception {
		if (StringUtils.isEmpty(keyword)) {
			return ERROR;
		}
		
		Map<String,String>  stockCodeMapping = (Map<String,String>)application.get("stockCodeMapping");
		if (stockCodeMapping == null || !stockCodeMapping.containsKey(keyword)) {
			return ERROR;
		}
		
		TStockDao stockDao = DaoFactory.getTStockDao();
		Stock stockParam = new Stock();
		stockParam.setCode(keyword);
		
		List<Stock> stocks =  stockDao.getStocks(stockParam);
		
		if (stocks != null) {
			Stock stock = stocks.get(0);
			request.put("stock", stock);
		} else {
			return ERROR;
		}
		
		
		return INPUT;
	}

	
	public void setKeyword(String keyword) {
		this.keyword = StringUtils.trim(keyword);
	}
	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}


	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

}
