package com.investdata.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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
import com.investdata.dao.TIndustryCategoryDao;
import com.investdata.dao.TStockDao;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.IndustryCategory;
import com.investdata.dao.po.Stock;
import com.investdata.dao.po.User;
import com.investdata.utils.FunctionWrapper;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 股票管理Action
 */
public class StocksMgrAction extends BaseAction implements RequestAware,SessionAware {
	private static String UPDATE_STOCK_INPUT = "update_stock_input";
	private static String STOCK_LIST = "stock_list";
	private static String ADD_STOCK_INPUT = "add_stock_input";
	private static String DATA_ADD_RESULT = "data_add_result"; 
	private static String DATA_UPDATE_RESULT = "data_update_result"; 
	
	Logger _log = Logger.getLogger(UserMgrAction.class);
	private Map<String,Object> request;
	private Map<String,Object> session;
	private String code;
	private Stock stock = new Stock();
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
	
	//加载股票基本信息
	public String loadStockInfo() throws Exception {
		TStockDao stockDao = DaoFactory.getTStockDao();
		Stock queryStock = new Stock();
		queryStock.setCode(code);
		List<Stock> stocks = stockDao.getStocks(queryStock);
		
		if (stocks != null && stocks.size() ==1) {
			Stock retValStock = stocks.get(0);
			jsonStock = new JSONObject();
			FunctionWrapper.convertObj2Json(retValStock, jsonStock);
			sendOutMsg(jsonStock);
		}
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
	
	//--->股票添加界面
	public String addStockInput() throws Exception {
		List<IndustryCategory> industryCategorys = getIndustryCategory();
		request.put("industryCategorys", industryCategorys);
		return ADD_STOCK_INPUT;
	}
	
	//新增股票信息
	public String addStock() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		TStockDao stockDao = DaoFactory.getTStockDao();
		stock.setInTime(new Timestamp(System.currentTimeMillis())); //入库时间-使用系统默认时间
		stockDao.add(stock);
		request.put("operMethod", methodName); //用方法名区分当前是添加的哪类数据
		
		return DATA_ADD_RESULT;
	}
	
	//获取行业信息
	public List<IndustryCategory> getIndustryCategory() throws Exception {
		TIndustryCategoryDao   industryDao = DaoFactory.getTIndustryCategoryDao();
		IndustryCategory industryCategory = new IndustryCategory();
		industryCategory.setFlag(1);
		List<IndustryCategory> industryCategorys = industryDao.getIndustryCategorys(industryCategory);
		return industryCategorys;
	}
	
	//加载行业列表--下拉框
	public String updateStockInput() throws Exception {
		List<IndustryCategory> industryCategorys = getIndustryCategory();
		request.put("industryCategorys", industryCategorys);
		return UPDATE_STOCK_INPUT;
	}
	
	public String updateStock() throws Exception {
		TStockDao stockDao = DaoFactory.getTStockDao();
		stockDao.update(stock);
		return DATA_UPDATE_RESULT;
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
