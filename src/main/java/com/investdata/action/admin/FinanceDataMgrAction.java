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
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONArray;
import org.json.JSONObject;

import com.investdata.common.BaseAction;
import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TBalanceSheetDao;
import com.investdata.dao.TCashFlowSheetDao;
import com.investdata.dao.TFinanceIndexInfoDao;
import com.investdata.dao.TGendataSheetDao;
import com.investdata.dao.TIncstateSheetDao;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.AdminUser;
import com.investdata.dao.po.BalanceSheet;
import com.investdata.dao.po.CashFlowSheet;
import com.investdata.dao.po.FinanceIndexInfo;
import com.investdata.dao.po.GendataSheet;
import com.investdata.dao.po.IncstateSheet;
import com.investdata.dao.po.User;
import com.investdata.utils.FunctionWrapper;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 财务数据Action
 */
public class FinanceDataMgrAction extends BaseAction implements RequestAware,SessionAware,ApplicationAware {
	//跳转标志符
	private static String UPDATE = "update"; 
	private static String ADD = "add"; 
	private static String ADD_BALANCE_INPUT = "add_balance_input"; 
	private static String UPDATE_BALANCE_INPUT = "update_balance_input"; 
	private static String ADD_CASHFLOW_INPUT = "add_cashFlow_input"; 
	private static String UPDATE_CASHFLOW_INPUT = "update_cashFlow_input"; 
	private static String ADD_GENDATA_INPUT = "add_genData_input"; 
	private static String DATA_ADD_RESULT = "data_add_result"; 
	private static String DATA_UPDATE_RESULT = "data_update_result"; 
	private static String UPDATE_GENDATA_INPUT = "update_genData_input"; 
	private static String ADD_INCSTATE_INPUT = "add_incstate_input"; 
	private static String UPDATE_INCSTATE_INPUT = "update_incstate_input"; 
	private static String QUERY_BALANCE_INPUT = "query_balance_input";
	private static String QUERY_INCSTATE_INPUT = "query_incstate_input";
	private static String QUERY_CASHFLOW_INPUT = "query_cashflow_input";
	private static String QUERY_GENDATA_INPUT = "query_gendata_input";
		
	private GendataSheet  genDataSheet = new GendataSheet();
	private CashFlowSheet cashFlowSheet = new CashFlowSheet();
	private IncstateSheet incstateSheet = new IncstateSheet();
	private BalanceSheet  balanceSheet = new BalanceSheet();
	JSONObject jsonBalance = null;
	JSONObject jsonIncstate = null;
	JSONObject jsonCashFlow = null;
	JSONObject jsonGendata = null;

	Logger _log = Logger.getLogger(UserMgrAction.class);
	private Map<String,Object> request;
	private Map<String,Object> session;
	private Map<String,Object> application;
	private JSONObject jsonUser;
	
	private int flag; //用户状态 0-停用  1-启用
	private String userName;
	private String code;
	private String year;
	private int loadFlag; //0-仅加载股票名称 1-加载财务数据及和股票名称
	
	//分页数据
	private int start;
	private int length;
	private int draw;
	
	
	//添加通用数据表项
	public String addGendata() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		AdminUser admUser = (AdminUser)session.get("admUser");
		if (admUser != null ) {
			genDataSheet.setModUser(admUser.getUserName());
			genDataSheet.setInTime(new Timestamp(System.currentTimeMillis()));
			TGendataSheetDao gendataDao = DaoFactory.getTGendataSheetDao();
			gendataDao.addGendataSheet(genDataSheet);
			
			request.put("operMethod", methodName); //用方法名区分当前是添加的哪类数据
			
			return DATA_ADD_RESULT;
		} else {
			return null; 
		}
	}
	
	//修改综合表项
	public String updateGendata() throws Exception {
		if (StringUtils.isEmpty(genDataSheet.getCode()) || StringUtils.isEmpty(genDataSheet.getYear())) {
			return null;
		}
		String methodName = (String)ActionContext.getContext().get("methodName");
		AdminUser admUser = (AdminUser)session.get("admUser");
		if (admUser != null ) {
			genDataSheet.setModUser(admUser.getUserName());
			genDataSheet.setInTime(new Timestamp(System.currentTimeMillis()));
			TGendataSheetDao gendataDao = DaoFactory.getTGendataSheetDao();
			gendataDao.updateGendataSheet(genDataSheet);
			
			request.put("operMethod", methodName); //用方法名区分当前是添加的哪类数据
			
			return DATA_UPDATE_RESULT;
		} else {
			return null; 
		}
	}
	
	//现金流量表数据新增
	public String addCashFlow() throws Exception {
		String methodName = (String)ActionContext.getContext().get("methodName");
		AdminUser admUser = (AdminUser)session.get("admUser");
		if (admUser != null ) {
			cashFlowSheet.setModUser(admUser.getUserName());
			cashFlowSheet.setInTime(new Timestamp(System.currentTimeMillis()));
			TCashFlowSheetDao cashFlowSheetDao = DaoFactory.getTCashFlowSheetDao();
			cashFlowSheetDao.addCashFlowSheet(cashFlowSheet);
			request.put("operMethod", methodName); //用方法名区分当前是添加的哪类数据
			
			return DATA_ADD_RESULT;
		} else {
			return null; 
		}
	}
	
	//修改现金流量表
	public String updateCashFlow() throws Exception {
		if (StringUtils.isEmpty(cashFlowSheet.getCode()) || StringUtils.isEmpty(cashFlowSheet.getYear())) {
			return null;
		}
		String methodName = (String)ActionContext.getContext().get("methodName");
		AdminUser admUser = (AdminUser)session.get("admUser");
		if (admUser != null ) {
			cashFlowSheet.setModUser(admUser.getUserName());
			cashFlowSheet.setInTime(new Timestamp(System.currentTimeMillis()));
			TCashFlowSheetDao cashFlowSheetDao = DaoFactory.getTCashFlowSheetDao();
			cashFlowSheetDao.updateCashFlowSheet(cashFlowSheet);
			
			request.put("operMethod", methodName); //用方法名区分当前是添加的哪类数据
			
			return DATA_UPDATE_RESULT;
		} else {
			return null; 
		}
	}
	
	//新增利润表数据
	public String addIncstate() throws Exception {
		if (StringUtils.isEmpty(incstateSheet.getCode()) || StringUtils.isEmpty(incstateSheet.getYear())) {
			return null;
		}
		String methodName = (String)ActionContext.getContext().get("methodName");
		AdminUser admUser = (AdminUser)session.get("admUser");
		if (admUser != null ) {
			incstateSheet.setModUser(admUser.getUserName());
			incstateSheet.setInTime(new Timestamp(System.currentTimeMillis()));
			TIncstateSheetDao incStateSheetDao = DaoFactory.getTIncstateSheetDao();
			incStateSheetDao.addIncstateSheet(incstateSheet);
			
			request.put("operMethod", methodName); //用方法名区分当前是添加的哪类数据
			
			return DATA_ADD_RESULT;
		} else {
			return null; 
		}
	}
	 
	
	//修改利润表数据
	public String updateIncstate() throws Exception {
		if (StringUtils.isEmpty(incstateSheet.getCode()) || StringUtils.isEmpty(incstateSheet.getYear())) {
			return null;
		}
		String methodName = (String)ActionContext.getContext().get("methodName");
		AdminUser admUser = (AdminUser)session.get("admUser");
		if (admUser != null ) {
			incstateSheet.setModUser(admUser.getUserName());
			incstateSheet.setInTime(new Timestamp(System.currentTimeMillis()));
			TIncstateSheetDao incStateSheetDao = DaoFactory.getTIncstateSheetDao();
			incStateSheetDao.updateIncstateSheet(incstateSheet);
			
			request.put("operMethod", methodName); //用方法名区分当前是添加的哪类数据
			
			return DATA_UPDATE_RESULT;
		} else {
			return null; 
		}
	}
	
	//添加资产负债表数据
	public String addBalance() throws Exception {
		if (StringUtils.isEmpty(balanceSheet.getCode()) || StringUtils.isEmpty(balanceSheet.getYear())) {
			return null;
		}
		String methodName = (String)ActionContext.getContext().get("methodName");
		AdminUser admUser = (AdminUser)session.get("admUser");
		if (admUser != null ) {
			balanceSheet.setModUser(admUser.getUserName());
			balanceSheet.setInTime(new Timestamp(System.currentTimeMillis()));
			TBalanceSheetDao balanceSheetDao = DaoFactory.getTBalanceSheetDao();
			balanceSheetDao.addBalanceSheet(balanceSheet);
			
			request.put("operMethod", methodName); //用方法名区分当前是添加的哪类数据
			
			return DATA_ADD_RESULT;
		} else {
			return null; 
		}
	}
	
	
	//修改利润表数据
	public String updateBalance() throws Exception {
		if (StringUtils.isEmpty(balanceSheet.getCode()) || StringUtils.isEmpty(balanceSheet.getYear())) {
			return null;
		}
		String methodName = (String)ActionContext.getContext().get("methodName");
		AdminUser admUser = (AdminUser)session.get("admUser");
		if (admUser != null ) {
			balanceSheet.setModUser(admUser.getUserName());
			balanceSheet.setInTime(new Timestamp(System.currentTimeMillis()));
			TBalanceSheetDao balanceSheetDao = DaoFactory.getTBalanceSheetDao();
			balanceSheetDao.updateBalanceSheet(balanceSheet);
			
			request.put("operMethod", methodName); //用方法名区分当前是添加的哪类数据
			
			return DATA_UPDATE_RESULT;
		} else {
			return null; 
		}
	}
	
	//加载资产负债表数据
	public String loadBalanceSheetData() throws Exception {
		jsonBalance = new JSONObject();
		
		if (loadFlag == 0) {
			getStockName2Json(jsonBalance,code);
		} else if (loadFlag == 1) {
			Map<String,String> stockCodeMaping = (Map<String,String>)application.get("stockCodeMapping");
			jsonBalance.put("stockName", stockCodeMaping.get(code));
			
			TBalanceSheetDao tsd = DaoFactory.getTBalanceSheetDao();
			BalanceSheet bst = new BalanceSheet();
			bst.setCode(code);
			bst.setYear(year);
			
			List<BalanceSheet> bsList = tsd.getBalanceSheets(bst);
			if (bsList != null && bsList.size() == 1) {
				BalanceSheet bstRetVal = bsList.get(0);
				FunctionWrapper.convertObj2Json(bstRetVal, jsonBalance);
			}
			getStockName2Json(jsonBalance,code);
			
		} else {
			_log.warn("未知的标志位请求");
		}
		sendOutMsg(jsonBalance);
		
		return AJAX;
	}
	
	//查询资产负债表列表
	public String queryBalanceSheetList() throws Exception {
		
		Map qryMap = new HashMap();
		qryMap.put("start", start);//起始记录
		qryMap.put("length", length);//每页展示条数
		qryMap.put("code", code);//股票代码
		qryMap.put("year", year);//年份，可以为空
		
		TBalanceSheetDao balanceSheetDao = DaoFactory.getTBalanceSheetDao();
		int recordsTotal = balanceSheetDao.getTotalCount(qryMap);
		int recordsFiltered = recordsTotal;
		
		jsonBalance = new JSONObject();
		jsonBalance.put("draw", draw);
		jsonBalance.put("recordsTotal", recordsTotal);
		jsonBalance.put("recordsFiltered", recordsFiltered);
		
		List<BalanceSheet> balanceSheetInfosList = balanceSheetDao.findBalanceSheetInfosByPage(qryMap);
		
		List<JSONObject> balanceSheetJsonList = new ArrayList<JSONObject>();
		for(BalanceSheet balSheet : balanceSheetInfosList) {
			JSONObject jsonBalListObj = new JSONObject();
			FunctionWrapper.convertObj2Json(balSheet, jsonBalListObj);
			balanceSheetJsonList.add(jsonBalListObj);
		}
		JSONArray jsonArray = new JSONArray(balanceSheetJsonList); 
		jsonBalance.put("data", jsonArray);
		
		sendOutMsg(jsonBalance);
		return AJAX;
	}
	
	
	//查询利润表数据列表（分页）
	public String queryIncstateList() throws Exception {
		
		Map qryMap = new HashMap();
		qryMap.put("start", start);//起始记录
		qryMap.put("length", length);//每页展示条数
		qryMap.put("code", code);//股票代码
		qryMap.put("year", year);//年份，可以为空		
		
		TIncstateSheetDao incstateDao = DaoFactory.getTIncstateSheetDao();
		int recordsTotal = incstateDao.getTotalCount(qryMap);
		int recordsFiltered = recordsTotal;
		
		jsonIncstate = new JSONObject();
		jsonIncstate.put("draw", draw);
		jsonIncstate.put("recordsTotal", recordsTotal);
		jsonIncstate.put("recordsFiltered", recordsFiltered);
		
		List<IncstateSheet> incstateSheetInfosList = incstateDao.findIncstateSheetInfosByPage(qryMap);
		
		List<JSONObject> incstateSheetJsonList = new ArrayList<JSONObject>();
		for(IncstateSheet incstateSheet : incstateSheetInfosList) {
			JSONObject jsonIncListObj = new JSONObject();
			FunctionWrapper.convertObj2Json(incstateSheet, jsonIncListObj);
			incstateSheetJsonList.add(jsonIncListObj);
		}
		JSONArray jsonArray = new JSONArray(incstateSheetJsonList); 
		jsonIncstate.put("data", jsonArray);
		
		sendOutMsg(jsonIncstate);
		return AJAX;
	}
	
	
	//查询现金流量表数据
	public String queryCashflowList() throws Exception {
		Map qryMap = new HashMap();
		qryMap.put("start", start);//起始记录
		qryMap.put("length", length);//每页展示条数
		qryMap.put("code", code);//股票代码
		qryMap.put("year", year);//年份，可以为空		
		
		TCashFlowSheetDao cashFlowDao = DaoFactory.getTCashFlowSheetDao();
		int recordsTotal = cashFlowDao.getTotalCount(qryMap);
		int recordsFiltered = recordsTotal;
		
		jsonCashFlow = new JSONObject();
		jsonCashFlow.put("draw", draw);
		jsonCashFlow.put("recordsTotal", recordsTotal);
		jsonCashFlow.put("recordsFiltered", recordsFiltered);
		
		List<CashFlowSheet> cashFlowSheetInfosList = cashFlowDao.findCashflowSheetInfosByPage(qryMap);
		
		List<JSONObject> cashFlowSheetJsonList = new ArrayList<JSONObject>();
		for(CashFlowSheet cashFlowSheet : cashFlowSheetInfosList) {
			JSONObject jsonCashFlowListObj = new JSONObject();
			FunctionWrapper.convertObj2Json(cashFlowSheet, jsonCashFlowListObj);
			cashFlowSheetJsonList.add(jsonCashFlowListObj);
		}
		JSONArray jsonArray = new JSONArray(cashFlowSheetJsonList); 
		jsonCashFlow.put("data", jsonArray);
		
		sendOutMsg(jsonCashFlow);
		return AJAX;
	}
	
	
	//加载综合数据表项数据
	public String queryGendataList() throws Exception {
		Map qryMap = new HashMap();
		qryMap.put("start", start);//起始记录
		qryMap.put("length", length);//每页展示条数
		qryMap.put("code", code);//股票代码
		qryMap.put("year", year);//年份，可以为空		
		
		TGendataSheetDao gendataDao = DaoFactory.getTGendataSheetDao();
		int recordsTotal = gendataDao.getTotalCount(qryMap);
		int recordsFiltered = recordsTotal;
		
		jsonGendata = new JSONObject();
		jsonGendata.put("draw", draw);
		jsonGendata.put("recordsTotal", recordsTotal);
		jsonGendata.put("recordsFiltered", recordsFiltered);
		
		List<GendataSheet> genDataSheetInfosList = gendataDao.findGendataSheetInfosByPage(qryMap);
		
		List<JSONObject> gendataSheetJsonList = new ArrayList<JSONObject>();
		for(GendataSheet gendataSheet : genDataSheetInfosList) {
			JSONObject jsonGendataListObj = new JSONObject();
			FunctionWrapper.convertObj2Json(gendataSheet, jsonGendataListObj);
			gendataSheetJsonList.add(jsonGendataListObj);
		}
		JSONArray jsonArray = new JSONArray(gendataSheetJsonList); 
		jsonGendata.put("data", jsonArray);
		
		sendOutMsg(jsonGendata);
		return AJAX;
	}
	
	//加载利润表数据
	public String loadIncstateSheetData() throws Exception {
		jsonIncstate = new JSONObject();
		Map<String,String> stockCodeMaping = (Map<String,String>)application.get("stockCodeMapping");
		jsonIncstate.put("stockName", stockCodeMaping.get(code));
		
		TIncstateSheetDao incstateSheetDao = DaoFactory.getTIncstateSheetDao();
		IncstateSheet is = new IncstateSheet();
		is.setCode(code);
		is.setYear(year);
		
		List<IncstateSheet> incstatesList = incstateSheetDao.getIncstateSheets(is);
		
		if (incstatesList !=null && incstatesList.size() == 1) {
			IncstateSheet isRetVal = incstatesList.get(0);
			FunctionWrapper.convertObj2Json(isRetVal, jsonIncstate);
		}
		sendOutMsg(jsonIncstate);
		
		return AJAX;
	}
	
	//加载现金流量表
	public String loadCashFlowSheetData() throws Exception {
		jsonCashFlow = new JSONObject();
		Map<String,String> stockCodeMaping = (Map<String,String>)application.get("stockCodeMapping");
		jsonCashFlow.put("stockName", stockCodeMaping.get(code));
		
		TCashFlowSheetDao cashFlowDao = DaoFactory.getTCashFlowSheetDao();
		CashFlowSheet cfs = new CashFlowSheet();
		cfs.setCode(code);
		cfs.setYear(year);
		
		List<CashFlowSheet> cashFlowList = cashFlowDao.getCashFlowSheets(cfs);
		
		if (cashFlowList !=null && cashFlowList.size() == 1) {
			CashFlowSheet cfsRetVal = cashFlowList.get(0);
			FunctionWrapper.convertObj2Json(cfsRetVal, jsonCashFlow);
		}
		sendOutMsg(jsonCashFlow);
		
		return AJAX;
	}
	
	//加载现金流量表
	public String loadGendataSheetData() throws Exception {
		jsonGendata = new JSONObject();
		Map<String,String> stockCodeMaping = (Map<String,String>)application.get("stockCodeMapping");
		jsonGendata.put("stockName", stockCodeMaping.get(code));
		
		TGendataSheetDao genDataDao = DaoFactory.getTGendataSheetDao();
		GendataSheet gs = new GendataSheet();
		gs.setCode(code);
		gs.setYear(year);
		
		List<GendataSheet> gendataList = genDataDao.getGendataSheets(gs);
		
		if (gendataList !=null && gendataList.size() == 1) {
			GendataSheet gsRetVal = gendataList.get(0);
			FunctionWrapper.convertObj2Json(gsRetVal, jsonGendata);
		}
		sendOutMsg(jsonGendata);
		
		return AJAX;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String addBalanceInput() throws Exception {
		return ADD_BALANCE_INPUT;
	}
	
	public String updateBalanceInput() throws Exception {
		return UPDATE_BALANCE_INPUT;
	}
	
	public String addCashFlowInput() throws Exception {
		return ADD_CASHFLOW_INPUT;
	}
	
	public String updateCashFlowInput() throws Exception {
		return UPDATE_CASHFLOW_INPUT;
	}
	
	public String addGendataInput() throws Exception {
		return ADD_GENDATA_INPUT;
	}
	
	public String updateGendataInput() throws Exception {
		return UPDATE_GENDATA_INPUT;
	}
	
	public String addIncstateInput() throws Exception {
		return ADD_INCSTATE_INPUT;
	}
	
	public String updateIncstateInput() throws Exception {
		return UPDATE_INCSTATE_INPUT;
	}
	
	public String queryBalanceInput() throws Exception {
		return QUERY_BALANCE_INPUT;
	}
	
	public String queryIncstateInput() throws Exception {
		return QUERY_INCSTATE_INPUT;
	}
	
	public String queryCashFlowInput() throws Exception {
		return QUERY_CASHFLOW_INPUT;
	}
	
	public String queryGendataInput() throws Exception {
		return QUERY_GENDATA_INPUT;
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
		
		TFinanceIndexInfoDao finIndxDao = DaoFactory.getTFinanceIndexInfoDao();
		int recordsTotal = finIndxDao.getTotalCount();
		int recordsFiltered = recordsTotal;
		
		jsonUser = new JSONObject();
		jsonUser.put("draw", draw);
		jsonUser.put("recordsTotal", recordsTotal);
		jsonUser.put("recordsFiltered", recordsFiltered);
		
		Map qryMap = new HashMap();
		qryMap.put("start", start);//起始记录
		qryMap.put("length", length);//每页展示条数
		List<FinanceIndexInfo> financeIndexInfosList = finIndxDao.findFinanceIndexInfosByPage(qryMap);
		
		List<JSONObject> financeIndexJsonList = new ArrayList<JSONObject>();
		for(FinanceIndexInfo indexInfo : financeIndexInfosList) {
			financeIndexJsonList.add(indexInfo.toJson());
		}
		JSONArray jsonArray = new JSONArray(financeIndexJsonList); 
		jsonUser.put("data", jsonArray);
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(jsonUser);
		
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
	
	public String update() throws Exception {
		return UPDATE;
	}
	
	public String add() throws Exception {
		return ADD;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setJsonUser(JSONObject jsonUser) {
		this.jsonUser = jsonUser;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public GendataSheet getGenDataSheet() {
		return genDataSheet;
	}

	public void setGenDataSheet(GendataSheet genDataSheet) {
		this.genDataSheet = genDataSheet;
	}
	
	public CashFlowSheet getCashFlowSheet() {
		return cashFlowSheet;
	}

	public void setCashFlowSheet(CashFlowSheet cashFlowSheet) {
		this.cashFlowSheet = cashFlowSheet;
	}
	
	public IncstateSheet getIncstateSheet() {
		return incstateSheet;
	}

	public void setIncstateSheet(IncstateSheet incstateSheet) {
		this.incstateSheet = incstateSheet;
	}
	
	public void setBalanceSheet(BalanceSheet balanceSheet) {
		this.balanceSheet = balanceSheet;
	}

	public BalanceSheet getBalanceSheet() {
		return balanceSheet;
	}
	
	public Map<String, Object> getApplication() {
		return application;
	}
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getLoadFlag() {
		return loadFlag;
	}

	public void setLoadFlag(int loadFlag) {
		this.loadFlag = loadFlag;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	public JSONObject getJsonBalance() {
		return jsonBalance;
	}

	public void setJsonBalance(JSONObject jsonBalance) {
		this.jsonBalance = jsonBalance;
	}
	
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	//返回股票名称Json代码
	public void getStockName2Json(JSONObject jsonObj, String code) throws Exception {
		Map<String,String> stockCodeMaping = (Map<String,String>)application.get("stockCodeMapping");
		jsonObj.put("stockName", stockCodeMaping.get(code));
	}
}
