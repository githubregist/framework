package org.framework.web.dispatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.framework.web.exception.ActionException;
import org.framework.web.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 配置文件的解析类
 * 
 * @author HJ
 */
public class ActionConfig {

	private final String CONFIG_FILE_NAME = "web_config.xml";

	private String filePath;

	private DocumentBuilderFactory factory = null;

	private DocumentBuilder builder = null;

	private Document document = null;

	/**
	 * 传入Servlet上下文(ServletContext)获取xml文件路径，解析xml
	 * 
	 * @param request
	 */
	public ActionConfig(ServletContext servletContext) {
		filePath = servletContext.getRealPath("/") + "WEB-INF\\classes\\"
				+ CONFIG_FILE_NAME;
		initConfig();
	}

	/**
	 * 传入xml文件路径，解析xml
	 * 
	 * @param request
	 */
	public ActionConfig(String fileName) {
		filePath = fileName;
		initConfig();
	}

	/**
	 * 初始化解析xml所需的变量
	 * 
	 */
	public void initConfig() {
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse(filePath);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Element getPackageName(String packageName) {
		return null;
	}

	/**
	 * 根据name属性寻找并返回action节点
	 * 
	 * @param actionName
	 * @return
	 */
	public Node getActionNode(String actionName) {
		Node action = null;
		NodeList nodes = document.getElementsByTagName("action");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			// 保证该action节点存在属性
			if (node.hasAttributes()) {
				if (ActionConfig.getAttribute(node, "name").equals(actionName)) {
					action = node;
					break;
				}
			}
		}
		if (action == null || "".equals(action)) {
			throw new ActionException(
					"Error in ActionConfig: cannot find any actions with name \'"
							+ actionName + "\'");
		}
		return action;
	}

	/**
	 * 返回名字为actionName的action的所有result(Map) 格式为: 名称(Stirng)-节点(Node)
	 * 
	 * @param actionName
	 * @return
	 */
	public Map getResultMap(String actionName) {
		Node action = getActionNode(actionName);
		Map<String, Node> resultMap = new HashMap<String, Node>();
		NodeList nodes = action.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equals("result")) {
				String resultName = XmlUtil.getAttributes(node).get(
						"name").toString();
				resultMap.put(resultName, node);
			}
		}
		return resultMap;
	}

	/**
	 * 根据节点(node)的属性名称(attrName)返回属性值(attrValue)
	 * 
	 * @param node
	 * @param attrName
	 * @return
	 */
	public static String getAttribute(Node node, String attrName) {
		String attrValue = null;
		// 节点的属性信息组成节点集
		NamedNodeMap attrs = node.getAttributes();
		// 遍历属性
		for (int j = 0; j < attrs.getLength(); j++) {
			// 属性集的元素(即节点)的nodeName为属性名，nodeValue为属性值
			Node attr = attrs.item(j);
			if (attrName.equals(attr.getNodeName())) {
				attrValue = attr.getNodeValue();
			}
		}
		return attrValue;
	}

	/**
	 * 寻找和action名字(唯一)对应的类名
	 * 
	 * @param actionName
	 * @return
	 */
	public String getClassName(String actionName) {
		String className = null;
		Node action = getActionNode(actionName);
		Map attrMap = XmlUtil.getAttributes(action);
		className = attrMap.get("class").toString();
		if (className == null || "".equals(className)) {
			throw new ActionException(
					"Error in ActionConfig: you have not defined a Class for action \'"
							+ actionName + "\'");
		}
		return className;
	}

	/**
	 * 寻找和action名字(唯一)对应的方法
	 * 
	 * @param actionName
	 * @return
	 */
	public String getMethodName(String actionName) {
		Node action = getActionNode(actionName);
		Map attrMap = XmlUtil.getAttributes(action);
		try {
			//	未为action定义methos时，attrMap取返回类型则发生异常，捕获之
			return attrMap.get("method").toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 传入action名称及result名称,返回该result对应的页面
	 * 
	 * @param actionName
	 * @param resultName
	 * @return
	 */
	public String getResultType(String actionName, String resultName) {
		Map resultMap = getResultMap(actionName);
		Node result = (Node) resultMap.get(resultName);
		Map attrMap = XmlUtil.getAttributes(result);
		try {
			//	未为result定义type时，attrMap取返回类型则发生异常，捕获之
			return attrMap.get("type").toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 传入action名称及result名称,返回该result对应的页面
	 * 
	 * @param actionName
	 * @param resultName
	 * @return
	 */
	public String getResultUrl(String actionName, String resultName) {
		Map resultMap = getResultMap(actionName);
		Node result = (Node) resultMap.get(resultName);
		return result.getChildNodes().item(0).getNodeValue();
	}

}