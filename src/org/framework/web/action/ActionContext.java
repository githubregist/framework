package org.framework.web.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * 封装了Servlet常用的上下文类
 * 以便于让用户定义的action类调用
 * 调用方式是调用ActionContext的静态方法
 * 如:获取request对象,ActionContext.getRequest()
 * 
 * @author HJ
 */
public class ActionContext {

	private static HttpServletRequest request;
	private static HttpServletResponse response;
	private static ServletContext servletContext;
	private static PageContext pageContext;
	
	private ActionContext() {
	}
	
	public static HttpServletRequest getRequest() {
		return request;
	}

	public static HttpServletResponse getResponse() {
		return response;
	}

	public static void setRequest(HttpServletRequest request) {
		ActionContext.request = request;
	}

	public static void setResponse(HttpServletResponse response) {
		ActionContext.response = response;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		ActionContext.servletContext = servletContext;
	}

	public static PageContext getPageContext() {
		return pageContext;
	}

	public static void setPageContext(PageContext pageContext) {
		ActionContext.pageContext = pageContext;
	}
		
}
