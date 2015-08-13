package org.framework.web.dispatcher;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.framework.web.result.Result;
import org.framework.web.settings.AttributeSettings;

/**
 * 根据处理的返回值，作进一步处理 跳转到返回页面，或交给其它Action继续处理
 * 
 * @author HJ
 */
public class ResultHandler {

	public ResultHandler() {

	}

	/**
	 * 将请求交给下一个Action继续处理
	 * 
	 * @param servletContext
	 * @param request
	 * @param response
	 * @param actionMapping
	 * @param result
	 * @throws ServletException
	 * @throws IOException
	 */
	private void nextAction(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping actionMapping, Result result)
			throws Exception {
		String name = result.getResultUrl();
		ActionMapping mapping = new ActionMapper().getActionMapping(
				servletContext, name);
		new ActionHandler().handle(servletContext, request, response,
				mapping);
	}

	public void handle(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping actionMapping, Result result)
			throws Exception {

		// 默认转发
		if (result.getType().equals(Result.DISPATCHER)) {
			// 自动将的返回值压入request
			try {
			AttributeSettings
					.pushAttributes(request, actionMapping.getAction());
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error in pushing attribute into request.");
			}
			RequestDispatcher rd = request.getRequestDispatcher(result
					.getResultUrl());
			rd.forward(request, response); // 转入
		}

		// 重定向
		else if (result.getType().equals(Result.REDIRECT)) {
			response.sendRedirect(result.getResultUrl().substring(1));
		}

		// action链,将保存前一个action为request中设置的attribute
		else if (result.getType().equals(Result.ACTION_CHAIN)) {
			// 自动将的返回值压入request
			AttributeSettings
					.pushAttributes(request, actionMapping.getAction());
			nextAction(servletContext,request,response,actionMapping,result);
		}

		// action重定向,清除所有request中设置的attribute
		else if (result.getType().equals(Result.ACTION_REDIRECT)) {
			// 清除所有request里的属性
			Enumeration attrs = request.getAttributeNames();
			while (attrs.hasMoreElements()) {
				String attrName = (String) attrs.nextElement();
				request.removeAttribute(attrName);
			}
			nextAction(servletContext,request,response,actionMapping,result);
		}
	}

}
