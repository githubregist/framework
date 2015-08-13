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
	 * 获取request请求的参数，并自动压入action中声明了setter方法的变量中
	 * 
	 * @param request
	 * @param action
	 */
	public static void pushParameters(HttpServletRequest request, Action action) {
		// 自动获取参数
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			String paramValue = (String) request.getParameter(paramName);
			Class clazz = action.getClass();
			Field[] fields = clazz.getDeclaredFields(); // 获得属性
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equals(paramName)) { // 定义了该参数
					Class paramType = fields[i].getType();
					try {
						String methodName = "set"
								+ paramName.substring(0, 1).toUpperCase()
								+ paramName.substring(1);
						Method paramMethod = clazz.getMethod(methodName,
								paramType);
						if (paramMethod != null) { // 并存在set方法
							paramMethod.invoke(action, paramValue); // 将参数压入定义的变量中
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
