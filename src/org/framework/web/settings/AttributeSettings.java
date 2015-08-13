package org.framework.web.settings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.framework.web.action.Action;

public class AttributeSettings {

	/**
	 * 获取action中声明了setter方法的变量的值，并自动压入到request的attribute中
	 * 
	 * @param request
	 * @param action
	 */
	public static void pushAttributes(HttpServletRequest request, Action action) {
		Class clazz = action.getClass();
		Field[] fields = clazz.getDeclaredFields(); // 遍历属性
		for (int i = 0; i < fields.length; i++) {
			String attrName = fields[i].getName();
			try {
				String methodName = "get"
						+ attrName.substring(0, 1).toUpperCase()
						+ attrName.substring(1);
				Method paramMethod = clazz.getMethod(methodName);
				if (paramMethod != null) { // 并存在set方法
					Object attrValue = paramMethod.invoke(action); // 取出属性值
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
