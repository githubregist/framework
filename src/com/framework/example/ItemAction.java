package com.framework.example;

import javax.servlet.http.HttpServletRequest;

import org.framework.web.action.ActionContext;
import org.framework.web.action.ActionSupport;

public class ItemAction extends ActionSupport {

	public String id;
	public String name;
	
	public String addItem() {
		HttpServletRequest request = ActionContext.getRequest();
		name = "motify in addItem";
		//id = "123";
		request.setAttribute("msg", "hello");
		return "success";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
