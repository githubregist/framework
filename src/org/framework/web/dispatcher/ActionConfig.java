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
 * �����ļ��Ľ�����
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
	 * ����Servlet������(ServletContext)��ȡxml�ļ�·��������xml
	 * 
	 * @param request
	 */
	public ActionConfig(ServletContext servletContext) {
		filePath = servletContext.getRealPath("/") + "WEB-INF\\classes\\"
				+ CONFIG_FILE_NAME;
		initConfig();
	}

	/**
	 * ����xml�ļ�·��������xml
	 * 
	 * @param request
	 */
	public ActionConfig(String fileName) {
		filePath = fileName;
		initConfig();
	}

	/**
	 * ��ʼ������xml����ı���
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
	 * ����name����Ѱ�Ҳ�����action�ڵ�
	 * 
	 * @param actionName
	 * @return
	 */
	public Node getActionNode(String actionName) {
		Node action = null;
		NodeList nodes = document.getElementsByTagName("action");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			// ��֤��action�ڵ��������
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
	 * ��������ΪactionName��action������result(Map) ��ʽΪ: ����(Stirng)-�ڵ�(Node)
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
	 * ���ݽڵ�(node)����������(attrName)��������ֵ(attrValue)
	 * 
	 * @param node
	 * @param attrName
	 * @return
	 */
	public static String getAttribute(Node node, String attrName) {
		String attrValue = null;
		// �ڵ��������Ϣ��ɽڵ㼯
		NamedNodeMap attrs = node.getAttributes();
		// ��������
		for (int j = 0; j < attrs.getLength(); j++) {
			// ���Լ���Ԫ��(���ڵ�)��nodeNameΪ��������nodeValueΪ����ֵ
			Node attr = attrs.item(j);
			if (attrName.equals(attr.getNodeName())) {
				attrValue = attr.getNodeValue();
			}
		}
		return attrValue;
	}

	/**
	 * Ѱ�Һ�action����(Ψһ)��Ӧ������
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
	 * Ѱ�Һ�action����(Ψһ)��Ӧ�ķ���
	 * 
	 * @param actionName
	 * @return
	 */
	public String getMethodName(String actionName) {
		Node action = getActionNode(actionName);
		Map attrMap = XmlUtil.getAttributes(action);
		try {
			//	δΪaction����methosʱ��attrMapȡ�������������쳣������֮
			return attrMap.get("method").toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ����action���Ƽ�result����,���ظ�result��Ӧ��ҳ��
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
			//	δΪresult����typeʱ��attrMapȡ�������������쳣������֮
			return attrMap.get("type").toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ����action���Ƽ�result����,���ظ�result��Ӧ��ҳ��
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