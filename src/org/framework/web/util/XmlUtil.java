package org.framework.web.util;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlUtil {

	public static Map getAttributes(Node node) {
		Map<String,String> attrMap = new HashMap<String,String>();
		String attrValue = null;
		String attrName = null;
		//	节点的属性信息组成节点集
		NamedNodeMap attrs = node.getAttributes();
		//	遍历属性
		for (int j = 0; j < attrs.getLength(); j++) {
			//	属性集的元素(即节点)的nodeName为属性名，nodeValue为属性值
			Node attr = attrs.item(j);
			attrName = attr.getNodeName();
			attrValue = attr.getNodeValue();
			attrMap.put(attrName, attrValue);
		}
		return attrMap;
	}
	
}
