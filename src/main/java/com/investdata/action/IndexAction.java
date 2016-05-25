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
import com.investdata.utils.StringUtils;

/**
 * @author HaiLong.Guo
 * @since 20160519
 * 跳转到首页Action
 */
public class IndexAction extends BaseAction implements RequestAware, SessionAware {
	private static final long serialVersionUID = -4003526420872337090L;
	private Logger _log = Logger.getLogger(IndexAction.class);
	private static StringBuilder stocksItems = new StringBuilder();
	private Map<String,Object> request = null;
	private Map<String,Object> session = null;
	
	public String execute() throws Exception {
		_log.info("进入主页加载股票列表数据流程");
		if (stocksItems.length() == 0) { //股票列表未被初始化.
			TStockDao stockDao = DaoFactory.getTStockDao();
			List<Stock> stocks = stockDao.getStocks(new Stock());
			if (stocks != null && stocks.size() > 0) {
				_log.info(String.format("股票列表数据加载完毕stocks.size=[%s]", stocks.size()));
				StringBuilder stockSelKey = new StringBuilder();
				stocksItems.append("[");
				for (int i=0; i< stocks.size(); i++) {
					Stock stock = stocks.get(i);
					String code = stock.getCode();
					String name = stock.getName();
					String shrotName = stock.getShortName();
					String market = stock.getMarket();
					/**
					 * 最终格式
					 * [
		                  {label: "key1", value: "v1" },
		                  {label: "key2", value: "v2" },
		               ];
					 */
					
					stockSelKey
							  .append("{label: \"")
							  .append(StringUtils.fillRSpace(shrotName, 13))
							  .append(StringUtils.fillRSpace(code, 13))
							  .append(StringUtils.fillRSpace(name, 13))
							  .append(market)
							  .append("\",")
							  .append(" value: \"")
							  .append(code)
							  .append("\"")
							  .append(" },")
							  .append("\n");
					
					stocksItems.append(stockSelKey);
					stockSelKey.setLength(0); //清空
					
				}
				stocksItems.deleteCharAt(stocksItems.length() -2 ); //删除最后一个逗号
				stocksItems.append(" ]");
			}else {
				_log.info(String.format("股票列表数据加载有误stocks=[%s]", stocks));
			}
		}
		
		_log.info(String.format("stocksList=[%s]\n", stocksItems.toString()));
		
		session.put("stocksItems", stocksItems.toString());
		return INPUT;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
		
	}
}
