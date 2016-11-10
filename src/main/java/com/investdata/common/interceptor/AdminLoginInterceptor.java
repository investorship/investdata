package com.investdata.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.investdata.common.factory.DaoFactory;
import com.investdata.dao.TMgrMenuDao;
import com.investdata.dao.po.AdminUser;
import com.investdata.dao.po.MgrMenu;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 管理员操作拦截器，对于后台管理菜单所有功能，
 * 如果没有登录，不得进行操作。
 * 
 * @author Administrator
 * 
 */
public class AdminLoginInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 1L;
	private static StringBuilder adminUrlBuilder = new StringBuilder();
	private static String ADMIN_LOGIN = "admin_login";
	

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
		
		//获取管理员用户登录会话信息
		AdminUser admUser = (AdminUser)session.getAttribute("admUser");
		
		//如果未初始化，则查询赋值。
		if (adminUrlBuilder.length() == 0 ) {
			TMgrMenuDao   mgrMenuDao = DaoFactory.getTMgrMenuDao();
			MgrMenu mgrMenu = new MgrMenu();
			mgrMenu.setIsLeaf(1);
			mgrMenu.setFlag(1);
			List<MgrMenu> menuList = mgrMenuDao.getMgrMenus(mgrMenu);
			String adminUrl = "";
			for (MgrMenu  mm : menuList) {
				adminUrl = mm.getReqUrl();
				adminUrlBuilder.append(adminUrl);
			}
		} 
		
		if (adminUrlBuilder.indexOf(actionUrl) != -1 && (admUser == null)) {
			return ADMIN_LOGIN;
		}
		
		return arg0.invoke();
	}
	
	public static void main(String[] args) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("abc").append("sdfms");
		
		
		
		System.err.println(sBuilder.indexOf("csdfs"));
	}
}
