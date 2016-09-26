package com.investdata.common.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TMgrMenuDao;
import com.investdata.dao.po.MgrMenu;
import com.investdata.dao.po.User;
import com.investdata.utils.LocalMac;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 *对于管理员相关url地址的请求要经过mac地址校验，防止数据篡改风险。
 * 
 * @author Guo.HaiLong
 * 
 */
public class AdminUrlMacValidInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		
		ActionContext actionContext = arg0.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		HttpSession session =  request.getSession();
		
		String url = request.getRequestURI();
		String actionUrl = "";
		
		if (!StringUtils.isEmpty(url)) {
			actionUrl = url.substring(url.lastIndexOf("/"));
		}
		
		//获取所有的后台url菜单地址
		List<String> adminUrlslList = getAdminUrlsList();
		if (adminUrlslList.contains(actionUrl)) {
			String userName = "";
			User user = (User)session.getAttribute("user");
			if (user != null) {
				userName = user.getUserName();
			}else {
				userName = request.getParameter("userName");
			}
			
			/*if (!StringUtils.isEmpty(userName)) { //非法的管理员用户
				return ERROR;
			}*/
			
			String mac = LocalMac.getLocalMac();
			System.err.println(mac);
			
			
		}
		
		/*//除了网站首页与股票明细界面之外，所有的指标请求都要求登录后才能使用
		if (!actionUrl.contains("index.action") && !actionUrl.contains("stock.action") && !actionUrl.contains("login.action")
				&& !actionUrl.contains("reg.action") && !actionUrl.contains("imageAuth.action") && !actionUrl.contains("checkLogin.action")) {
			User user = (User)session.getAttribute("user");
			if (user == null) { //跳转到登录界面
				return "login";
			}
		}*/
		
		return arg0.invoke();
	}
	
	
	private List<String> getAdminUrlsList() throws Exception {
		List<String> adminUrlsList = new ArrayList<String>();
		/*TMgrMenuDao mgrMenuDao = DaoFactory.getTMgrMenuDao();
		MgrMenu mgrMenu = new MgrMenu();
		mgrMenu.setFlag(1);
		List <MgrMenu> menusList = mgrMenuDao.getMgrMenus(mgrMenu);
		for ( MgrMenu menu : menusList) { //后台管理菜单
			adminUrlsList.add(menu.getReqUrl());
			
		}*/
		

		adminUrlsList.add("/adminLogin!adminLogin.action"); //后台管理员登录
		
		return adminUrlsList;
	}
	
	

}
