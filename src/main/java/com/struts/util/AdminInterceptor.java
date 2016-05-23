package com.struts.util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AdminInterceptor extends AbstractInterceptor implements StrutsStatics {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
		HttpSession session = request.getSession();
		
		// Validate the Administrator user and time out for every a AdminInterceptor request
		Object user = session.getAttribute(ApplicationConstant.USER_SESSION);
		if (null != user && user.equals("Administrator") ) {
			return invocation.invoke();
		} else {
			return "login";
		}
	}

}
