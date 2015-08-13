package org.framework.web.action;

/**
 * 用户通过继承这个类以实现对请求的处理
 * 作用相当于struts2中的ActionSupport
 * 用户可以重写execute方法或自定义方法
 * 
 * @author HJ
 */
public class ActionSupport implements Action {

	public String execute() throws Exception {
		return null;
	}

}
