package com.investdata.dao;

import java.util.List;

import com.investdata.dao.po.Stock;


public interface TStockDao {
	
	public void add(Stock stock) throws Exception;
	
	public List<Stock> getStocks(Stock stock) throws Exception;
	
}
