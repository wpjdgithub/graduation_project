package Grad.Service.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMTool {
	/*
	 * 从一个节点开始进行层次遍历
	 */
	public static String traverse(Node node){
		StringBuilder sb = new StringBuilder();
		Element nodeElement = (Element)node;
		String maincontent = nodeElement.getAttribute("nameCN")+":"+nodeElement.getAttribute("value");
		sb.append(maincontent);
		sb.append(";");
		NodeList childlist = node.getChildNodes();
		int length = childlist.getLength();
		for(int i = 0;i < length;i++){
			Node child = childlist.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE){
				String s = traverse(child);
				sb.append(s);
			}
		}
		String result = sb.toString();
		return result;
	}
}
