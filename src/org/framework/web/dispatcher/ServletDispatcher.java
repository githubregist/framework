package org.framework.web.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;

import org.framework.web.action.ActionContext;

/**
 * 主控制器
 * 主要工作有：根据请求寻找对应的Action
 * 设置上下文相关信息
 * 将请求交给ActionHanler处理
 * 
 * @author HJ
 */
public class ServletDispatcher extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 设置action上下文
		ActionContext.setRequest(request);
		ActionContext.setResponse(response);
		ActionContext.setServletContext(getServletContext());
		ActionContext.setPageContext(JspFactory.getDefaultFactory()
				.getPageContext(this, request, response, null, true,
						JspWriter.DEFAULT_BUFFER, true));
		//	从请求地址中提取action名称
		String url = request.getRequestURL().toString();
		String actionName = url.substring(url.lastIndexOf("/") + 1, url
				.lastIndexOf("action") - 1);
		ActionMapping actionMapping = new ActionMapper().getActionMapping(
				getServletContext(), actionName);
		ActionHandler actionHandler = new ActionHandler();
		//	交给对应的action处理
		try {
			actionHandler.handle(getServletContext(), request, response,
				actionMapping);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
