package com.framework.example;

import org.framework.web.dispatcher.ActionConfig;

public class XmlDemo {

	public static void main(String[] args){
		ActionConfig xmlDoc = new ActionConfig("src\\web_config.xml");
		String className = xmlDoc.getClassName("addItem");
		System.out.println(className);
		String pathName = xmlDoc.getResultUrl("addItem", "success");
		System.out.println(pathName);
	}
}
