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
import com.investdata.dao.TFinanceIndexInfoDao;
import com.investdata.dao.TGendataSheetDao;
import com.investdata.dao.TUserDao;
import com.investdata.dao.po.AdminUser;
import com.investdata.dao.po.FinanceIndexInfo;
import com.investdata.dao.po.GendataSheet;
import com.investdata.dao.po.User;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 财务数据Action
 */
public class FinanceDataMgrAction extends BaseAction implements RequestAware,SessionAware {
	//跳转标志符
	private static String UPDATE = "update";
	private static String ADD = "add";
	private static String ADD_BALANCE_INPUT = "add_balance_input"; 
	private static String UPDATE_BALANCE_INPUT = "update_balance_input"; 
	private static String ADD_CASHFLOW_INPUT = "add_cashFlow_input"; 
	private static String UPDATE_CASHFLOW_INPUT = "update_cashFlow_input"; 
	private static String ADD_GENDATA_INPUT = "add_genData_input"; 
	private static String DATA_ADD_RESULT = "data_add_result"; 
	private static String UPDATE_GENDATA_INPUT = "update_genData_input"; 
	private static String ADD_INCSTATE_INPUT = "add_incstate_input"; 
	private static String UPDATE_INCSTATE_INPUT = "update_incstate_input"; 
	
	private GendataSheet genDataSheet = new GendataSheet();

	Logger _log = Logger.getLogger(UserMgrAction.class);
	private Map<String,Object> request;
	private Map<String,Object> session;
	private JSONObject jsonUser;
	private int flag; //用户状态 0-停用  1-启用
	private String userName;
	
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

}
