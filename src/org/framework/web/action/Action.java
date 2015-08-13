package org.framework.web.action;

/**
 * 处理类(Action)接口
 * 处理的默认入口方法及若干默认的返回类型值
 * 
 * @author HJ
 */
public interface Action {

	public static final String SUCCESS = "success";

	public static final String INPUT = "input";

	public static final String ERROR = "error";

	public static final String LOGIN = "login";

	public static final String NONE = "none";

	/**
	 * 处理请求的默认访问入口
	 * 
	 * @return
	 * @throws Exception
	 */
	public String execute() throws Exception;

}
