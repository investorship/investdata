package com.investdata.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONArray;
import org.json.JSONObject;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TStockDao;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.Stock;
import com.investdata.dao.po.User;
import com.investdata.utils.StringUtils;

/**
 * 股票管理Action
 */
public class StocksMgrAction extends BaseAction implements RequestAware,SessionAware {
	private static String UPDATE_STOCK_INPUT = "update_stock_input";
	private static String STOCK_LIST = "stock_list";
	private static String ADD_STOCK = "add_stock";
	
	Logger _log = Logger.getLogger(UserMgrAction.class);
	private Map<String,Object> request;
	private Map<String,Object> session;
	private JSONObject jsonStock;
	private int flag; //用户状态 0-停用  1-启用
	

	private String userName;

	public String execute() throws Exception {
		return STOCK_LIST;
	}
	
	/**
	 * 分页查询
	 * json格式如下：
	 * {
	 *		"draw":1,
	 *		"recordsTotal":1,
	 *		"recordsFiltered":1,
	 *		"data":"
	 *					[
	 *						{
	 *							"flag":0,
	 *							"inTime":"2016-06-05 19:20:02.0",
	 *							"email":"developerli@126.com",
	 *							"isPayer":0,
	 *							"userName":"hahalzb",
	 *							"activeCode":"d8cd730be8edcb785f51e11cdf379c95",
	 *							"password":"DC16Pe+/vT/vv73vv70="
	 *						}
	 *					]
	 *	}
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		HttpServletRequest httpRequest = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json, charset=utf-8");
		
		int start = Integer.parseInt(StringUtils.trim(httpRequest.getParameter("start")));
		int length = Integer.parseInt(StringUtils.trim(httpRequest.getParameter("length")));
		int draw = Integer.parseInt(StringUtils.trim(httpRequest.getParameter("draw")));
		
		TStockDao stockDao = DaoFactory.getTStockDao();
		int recordsTotal = stockDao.getTotalCount();
		int recordsFiltered = recordsTotal;
		
		
		jsonStock = new JSONObject();
		jsonStock.put("draw", draw);
		jsonStock.put("recordsTotal", recordsTotal);
		jsonStock.put("recordsFiltered", recordsFiltered);
		
		Map qryMap = new HashMap();
		qryMap.put("start", start);//起始记录
		qryMap.put("length", length);//每页展示条数
		List<Stock> stocksList = stockDao.findStocksByPage(qryMap);
		
		List<JSONObject> stocksJsonList = new ArrayList<JSONObject>();
		for(Stock stock : stocksList) {
			stocksJsonList.add(stock.toJson());
		}
		JSONArray jsonArray = new JSONArray(stocksJsonList); 
		jsonStock.put("data", jsonArray);
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(jsonStock);
		
		out.close();
		
		return AJAX;
	}
	
	public String updateState() throws Exception {
		TUserDao userDao = DaoFactory.getTUserDao();
		User user = new User();
		user.setUserName(userName);
		user.setFlag(flag);
		userDao.update(user);
		
		return SUCCESS;
	}
	
	public String updateStock() throws Exception {
		
		return UPDATE_STOCK_INPUT;
	}
	
	public String addStock() throws Exception {
		return ADD_STOCK;
	}
	

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setJsonUser(JSONObject jsonUser) {
		this.jsonStock = jsonUser;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}

}
