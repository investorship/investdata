package com.investdata.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TStockDao;
import com.investdata.dao.po.Stock;

/**
 * 跳转到首页Action
 */
public class IndexAction extends BaseAction implements RequestAware, SessionAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(IndexAction.class);
	private static StringBuilder stocksList = new StringBuilder();
	private Map<String,Object> request = null;
	
	public String execute() throws Exception {
		_log.info("进入主页加载股票列表数据流程");
		if (stocksList.length() == 0) { //股票列表未被初始化.
			TStockDao stockDao = DaoFactory.getTStockDao();
			List<Stock> stocks = stockDao.getStocks(new Stock());
			if (stocks != null && stocks.size() > 0) {
				_log.info(String.format("股票列表数据加载完毕stocks.size=[%s]", stocks.size()));
				
				for (Stock stock : stocks) {
					String code = stock.getCode();
					String name = stock.getName();
					String shrotName = stock.getShortName();
					String market = stock.getMarket();
				}
				
				
			}else {
				_log.info(String.format("股票列表数据加载有误stocks=[%s]", stocks));
			}
			
			
		}
		
		
		if (stocksList.length() == 0) {
			stocksList.append("[\"HLMD    600256    海立美达    A股    深圳\"").append(",").append("\n");
			stocksList.append("\"SHFZ    600254    双汇发展    A股    上海\"]");			
		}
		
		request.put("stockData", stocksList.toString());
		return INPUT;
	}

	public void setSession(Map<String, Object> session) {
		
		
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
		
	}
	
	public static void main(String[] args) {
		StringBuilder s1 = new StringBuilder();
		s1.append("[\"HLMD   600256	 海立美达      A股      深圳\"").append(",").append("\n");
		s1.append("\"SHFZ   600254	 双汇发展      A股      上海\"]");	
		System.err.println(s1);
	}
}
