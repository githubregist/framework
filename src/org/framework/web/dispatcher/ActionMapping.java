package org.framework.web.dispatcher;

import org.framework.web.action.Action;

public class ActionMapping {
	
	private String name; 	// action的映射名称
	private Action action; 	// action实例
	private String className;	// 类全称
	private String method; 	// 入口方法

	public ActionMapping() {
		
	}
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
