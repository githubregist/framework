package com.framework.example;

import javax.servlet.http.HttpServletRequest;

import org.framework.web.action.ActionContext;
import org.framework.web.action.ActionSupport;

public class OrderAction extends ActionSupport {

	public String id;
	public String name;
	
	public String addOrder() {
		HttpServletRequest request = ActionContext.getRequest();
		name = request.getParameter("name");
		String msg = (String)request.getAttribute("msg");
		System.out.println(id + ":" + getName() + "in OrderAction");
		System.out.println("item say:" + msg + "to me");
		return SUCCESS;
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
