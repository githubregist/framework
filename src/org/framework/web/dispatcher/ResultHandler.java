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
 * ���ݴ���ķ���ֵ������һ������ ��ת������ҳ�棬�򽻸�����Action��������
 * 
 * @author HJ
 */
public class ResultHandler {

	public ResultHandler() {

	}

	/**
	 * �����󽻸���һ��Action��������
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

		// Ĭ��ת��
		if (result.getType().equals(Result.DISPATCHER)) {
			// �Զ����ķ���ֵѹ��request
			try {
			AttributeSettings
					.pushAttributes(request, actionMapping.getAction());
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error in pushing attribute into request.");
			}
			RequestDispatcher rd = request.getRequestDispatcher(result
					.getResultUrl());
			rd.forward(request, response); // ת��
		}

		// �ض���
		else if (result.getType().equals(Result.REDIRECT)) {
			response.sendRedirect(result.getResultUrl().substring(1));
		}

		// action��,������ǰһ��actionΪrequest�����õ�attribute
		else if (result.getType().equals(Result.ACTION_CHAIN)) {
			// �Զ����ķ���ֵѹ��request
			AttributeSettings
					.pushAttributes(request, actionMapping.getAction());
			nextAction(servletContext,request,response,actionMapping,result);
		}

		// action�ض���,�������request�����õ�attribute
		else if (result.getType().equals(Result.ACTION_REDIRECT)) {
			// �������request�������
			Enumeration attrs = request.getAttributeNames();
			while (attrs.hasMoreElements()) {
				String attrName = (String) attrs.nextElement();
				request.removeAttribute(attrName);
			}
			nextAction(servletContext,request,response,actionMapping,result);
		}
	}

}
