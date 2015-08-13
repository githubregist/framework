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
 * ��������
 * ��Ҫ�����У���������Ѱ�Ҷ�Ӧ��Action
 * ���������������Ϣ
 * �����󽻸�ActionHanler����
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

		// ����action������
		ActionContext.setRequest(request);
		ActionContext.setResponse(response);
		ActionContext.setServletContext(getServletContext());
		ActionContext.setPageContext(JspFactory.getDefaultFactory()
				.getPageContext(this, request, response, null, true,
						JspWriter.DEFAULT_BUFFER, true));
		//	�������ַ����ȡaction����
		String url = request.getRequestURL().toString();
		String actionName = url.substring(url.lastIndexOf("/") + 1, url
				.lastIndexOf("action") - 1);
		ActionMapping actionMapping = new ActionMapper().getActionMapping(
				getServletContext(), actionName);
		ActionHandler actionHandler = new ActionHandler();
		//	������Ӧ��action����
		try {
			actionHandler.handle(getServletContext(), request, response,
				actionMapping);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
