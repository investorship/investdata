package com.investdata.dao.impl;

import java.util.List;

import com.investdata.dao.TStockDao;
import com.investdata.dao.po.Stock;

public class StockDaoImpl extends BaseDao implements TStockDao {
	
	public void add(Stock stock) throws Exception {
		
	}
	
	/**
	 * 返回股票信息
	 */
	public List<Stock> getStocks(Stock stock) throws Exception {
		List<Stock> stocks = dao.queryForList("stock.getAllStocks", stock);
		return stocks;
	}

}
