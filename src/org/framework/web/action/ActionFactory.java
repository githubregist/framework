package org.framework.web.action;

import org.framework.web.dispatcher.ActionMapping;

/**
 * 获取action实例的工厂类
 * 
 * @author HJ
 */
public class ActionFactory {
	
	/**
	 * 根据传入的ActionMapping实例，获取action的名称
	 * 通过反射机制，实例化Action对象并返回该Action实例
	 * 
	 * @param actionMapping
	 * @return
	 */
	public static Action newInstance(ActionMapping actionMapping) {
		Action action = null;
		try {
			Class actionClass = Class.forName(actionMapping.getClassName());
			action = (Action)actionClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return action;
	}

}
