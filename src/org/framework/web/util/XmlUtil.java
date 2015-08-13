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
		//	�ڵ��������Ϣ��ɽڵ㼯
		NamedNodeMap attrs = node.getAttributes();
		//	��������
		for (int j = 0; j < attrs.getLength(); j++) {
			//	���Լ���Ԫ��(���ڵ�)��nodeNameΪ��������nodeValueΪ����ֵ
			Node attr = attrs.item(j);
			attrName = attr.getNodeName();
			attrValue = attr.getNodeValue();
			attrMap.put(attrName, attrValue);
		}
		return attrMap;
	}
	
}
