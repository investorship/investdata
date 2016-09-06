package com.investdata.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.investdata.dao.po.User;
import com.investdata.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 登录拦截器，需要登录的操作，如果没有登录，跳转到登录界面
 * 
 * @author Administrator
 * 
 */
public class LoginInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub

		ActionContext actionContext = arg0.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		HttpSession session =  request.getSession();
		
		String url = request.getRequestURI();
		String actionUrl = "";
		if (!StringUtils.isEmpty(url)) {
			actionUrl = url.substring(url.lastIndexOf("/"));
		}
		
		//除了网站首页与股票明细界面之外，所有的指标请求都要求登录后才能使用
		if (!actionUrl.contains("index.action") && !actionUrl.contains("stock.action") && !actionUrl.contains("login.action")
				&& !actionUrl.contains("reg.action") && !actionUrl.contains("imageAuth.action") && !actionUrl.contains("checkLogin.action")) {
			User user = (User)session.getAttribute("user");
			if (user == null) { //跳转到登录界面
				return "login";
			}
		}
		
		return arg0.invoke();
	}
}
