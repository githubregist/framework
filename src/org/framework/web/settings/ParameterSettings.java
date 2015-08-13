package org.framework.web.settings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.framework.web.action.Action;

public class ParameterSettings {
	
	public ParameterSettings() {
		
	}

	/**
	 * ��ȡrequest����Ĳ��������Զ�ѹ��action��������setter�����ı�����
	 * 
	 * @param request
	 * @param action
	 */
	public static void pushParameters(HttpServletRequest request, Action action) {
		// �Զ���ȡ����
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			String paramValue = (String) request.getParameter(paramName);
			Class clazz = action.getClass();
			Field[] fields = clazz.getDeclaredFields(); // �������
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equals(paramName)) { // �����˸ò���
					Class paramType = fields[i].getType();
					try {
						String methodName = "set"
								+ paramName.substring(0, 1).toUpperCase()
								+ paramName.substring(1);
						Method paramMethod = clazz.getMethod(methodName,
								paramType);
						if (paramMethod != null) { // ������set����
							paramMethod.invoke(action, paramValue); // ������ѹ�붨��ı�����
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
