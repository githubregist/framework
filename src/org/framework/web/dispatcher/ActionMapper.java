package org.framework.web.dispatcher;

import javax.servlet.ServletContext;

public class ActionMapper {

	ActionMapping actionMapping;
	
	public ActionMapper() {
		
	}

	/**
	 * ��ȡһ��ActionMappingʵ��
	 * 
	 * @param servletContext
	 * @param actionName
	 * @return
	 */
	public ActionMapping getActionMapping(ServletContext servletContext,String actionName) {
		ActionConfig config = new ActionConfig(servletContext);
		
		//	�������ļ��л�ȡaction��Ϣ
		String className = config.getClassName(actionName);
		String methodName = config.getMethodName(actionName);
		
		ActionMapping actionMapping = new ActionMapping();
		actionMapping.setName(actionName);
		actionMapping.setClassName(className);
		actionMapping.setMethod(methodName);
		return actionMapping;
	}

	public void setActionMapping(ActionMapping actionMapping) {
		this.actionMapping = actionMapping;
	}
	
}
