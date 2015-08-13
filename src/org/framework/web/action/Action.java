package org.framework.web.action;

/**
 * ������(Action)�ӿ�
 * �����Ĭ����ڷ���������Ĭ�ϵķ�������ֵ
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
	 * ���������Ĭ�Ϸ������
	 * 
	 * @return
	 * @throws Exception
	 */
	public String execute() throws Exception;

}
