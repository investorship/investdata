package com.investdata.dao;

import java.util.List;
import java.util.Map;

import com.investdata.dao.po.Stock;


public interface TStockDao {
	
	public void add(Stock stock) throws Exception;
	
	public void update(Stock stock)throws Exception;
	
	public List<Stock> getStocks(Stock stock) throws Exception;
	
	public int getTotalCount() throws Exception;
	
	public List<Stock> findStocksByPage(Map map) throws Exception;
	
}
