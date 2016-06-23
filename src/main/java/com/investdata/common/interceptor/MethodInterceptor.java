package com.investdata.common.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 方法拦截器，获取动态方法调用时执行的方法名称
 * 用于在redis缓存时 key的一部分(标识是哪个财务指标)
 * @author Administrator
 *
 */
public class MethodInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String methodName = invocation.getProxy().getMethod();
		ActionContext.getContext().put("methodName", methodName);
		return invocation.invoke();
	}

}
