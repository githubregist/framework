package org.framework.web.dispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.framework.web.action.Action;
import org.framework.web.action.ActionFactory;
import org.framework.web.result.Result;
import org.framework.web.result.ResultFactory;
import org.framework.web.settings.ParameterSettings;

public class ActionHandler {

	private Action action = null;

	private Method method = null;

	private String resultName = null;

	private Result result = new Result();

	private ResultHandler resultHandler = new ResultHandler();

	public ActionHandler() {

	}

	public void handle(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping actionMapping) throws Exception {
		// ʵ����Action������
		try {
			action = ActionFactory.newInstance(actionMapping);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error in creating a Action.");
		}
		actionMapping.setAction(action);

		// ʵ��������ȡ��ڷ���
		try {
			method = action.getClass().getDeclaredMethod(
					actionMapping.getMethod());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		// �������action�Զ���ȡ�������
		try {
			ParameterSettings.pushParameters(request, action);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errors occur in pushing Parameters into the action you defined.");
		}
		
		// ִ����ڷ�������ȡ����ֵ
		if (method != null) {
			try {
				resultName = (String) method.invoke(action);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		// ���ݷ���ֵ��������ҳ����ת,��������Action����
		result = ResultFactory.getResult(servletContext, actionMapping
				.getName(), resultName);
		resultHandler.handle(servletContext, request, response, actionMapping,
				result);
	}

}
