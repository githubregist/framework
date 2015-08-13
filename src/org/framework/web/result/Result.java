package org.framework.web.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Result {

	public final static String DISPATCHER = "dispatcher";
	public final static String REDIRECT = "redirect";
	public final static String ACTION_REDIRECT = "action-redirect";
	public final static String ACTION_CHAIN = "action-chain";
	
	private String name; 		// 结果名称
	private String type; 		// 结果类型,默认dispatcher
								//redirect/action-redirect/action-chain
	private String resultUrl; 	// 结果匹配url
	private Map parameters; 	// 处理跳转时所带的参数
	
	public Result() {
		
	}
	
	public boolean checkResultType() {
		boolean state = false;
		List<String> resultTypes = new ArrayList<String>();
		resultTypes.add(DISPATCHER);
		resultTypes.add(REDIRECT);
		resultTypes.add(ACTION_REDIRECT);
		resultTypes.add(ACTION_CHAIN);
		if(type != null && !type.equals("") && resultTypes.contains(type)) {
			state = true;
		}
		return state;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map getParameters() {
		return parameters;
	}
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getResultUrl() {
		return resultUrl;
	}

	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}

}
