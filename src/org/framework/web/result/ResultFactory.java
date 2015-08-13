package org.framework.web.result;

import javax.servlet.ServletContext;

import org.framework.web.dispatcher.ActionConfig;

/**
 * 获取Result类实例的工厂类
 * 
 * @author HJ
 */
public class ResultFactory {

	private static final String DEFAULT_RESULT_TYPE = "dispatcher";
	
	public static Result getResult(ServletContext servletContext,
			String actionName, String resultName) throws Exception {
		ActionConfig config = new ActionConfig(servletContext);
		Result result = new Result();
		String resultType = null;
		String resultUrl = null;
		
		try {
			resultType = config.getResultType(actionName, resultName);
			resultUrl = config.getResultUrl(actionName, resultName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error in constructing return Result.");
		}
		
		result.setName(resultName);
		result.setType(resultType);
		if (!result.checkResultType()) {
			result.setType(DEFAULT_RESULT_TYPE);
		}
		result.setResultUrl(resultUrl);
		return result;
	}
	
}
