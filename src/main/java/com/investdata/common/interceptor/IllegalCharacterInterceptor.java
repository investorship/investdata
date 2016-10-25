package com.investdata.common.interceptor;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 非法字符拦截器，对于恶意的脚本数据进行相关的替换
 * 例如： <script> </script>
 * 会将 <> 尖括号替换为&lt;
 * 
 * 由于无法替换request中,paramMap中页面提交过来的数据，暂改为在action中进行过滤
 *
 */
public class IllegalCharacterInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		Map <String,String[]> requestParams = request.getParameterMap();
		Set<String> keys = requestParams.keySet();
		for(String key: keys) {
			String paramVal = StringUtils.trim(request.getParameter(key));
			paramVal = paramVal.replaceAll("<", "&lt");
			paramVal = paramVal.replaceAll(">", "&gt");
			
			System.err.println("String==" + key + "value=" + request.getParameter(key));
		}
		
		return invocation.invoke();
	}

}
