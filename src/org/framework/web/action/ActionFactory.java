package org.framework.web.action;

import org.framework.web.dispatcher.ActionMapping;

/**
 * ��ȡactionʵ���Ĺ�����
 * 
 * @author HJ
 */
public class ActionFactory {
	
	/**
	 * ���ݴ����ActionMappingʵ������ȡaction������
	 * ͨ��������ƣ�ʵ����Action���󲢷��ظ�Actionʵ��
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
