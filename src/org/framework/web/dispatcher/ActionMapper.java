package org.framework.web.dispatcher;

import javax.servlet.ServletContext;

public class ActionMapper {

	ActionMapping actionMapping;
	
	public ActionMapper() {
		
	}

	/**
	 * 获取一个ActionMapping实例
	 * 
	 * @param servletContext
	 * @param actionName
	 * @return
	 */
	public ActionMapping getActionMapping(ServletContext servletContext,String actionName) {
		ActionConfig config = new ActionConfig(servletContext);
		
		//	从配置文件中获取action信息
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
