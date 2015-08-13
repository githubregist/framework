package org.framework.web.settings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.framework.web.action.Action;

public class AttributeSettings {

	/**
	 * ��ȡaction��������setter�����ı�����ֵ�����Զ�ѹ�뵽request��attribute��
	 * 
	 * @param request
	 * @param action
	 */
	public static void pushAttributes(HttpServletRequest request, Action action) {
		Class clazz = action.getClass();
		Field[] fields = clazz.getDeclaredFields(); // ��������
		for (int i = 0; i < fields.length; i++) {
			String attrName = fields[i].getName();
			try {
				String methodName = "get"
						+ attrName.substring(0, 1).toUpperCase()
						+ attrName.substring(1);
				Method paramMethod = clazz.getMethod(methodName);
				if (paramMethod != null) { // ������set����
					Object attrValue = paramMethod.invoke(action); // ȡ������ֵ
					System.out.println(attrName + ":" + attrValue);
					request.setAttribute(attrName, attrValue);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				//e.printStackTrace();
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
