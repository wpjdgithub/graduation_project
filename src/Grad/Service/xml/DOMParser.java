package Grad.Service.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {
	private DocumentBuilderFactory builderFactory ;
	public DOMParser(){
		this.builderFactory = DocumentBuilderFactory.newInstance();
	}
	public Document parse(String filepath){
		Document document = null; 
	    try { 
	       DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
	       document = builder.parse(new File(filepath)); 
	    } catch (ParserConfigurationException e) { 
	       e.printStackTrace();  
	    } catch (SAXException e) { 
	       e.printStackTrace(); 
	    } catch (IOException e) { 
	       e.printStackTrace(); 
	    } 
	    return document; 
	}
	//Test
	public static void main(String[] args){
		DOMParser parser = new DOMParser();
		Document document = parser.parse("wenshudata/天津文书婚姻纠纷/变更抚养关系纠纷/(2001)南民初字第5905号民事判决书（一审民事案件用）.RTF.xml");
		Element rootElement = document.getDocumentElement();
		//traverse child elements
		NodeList nodes = rootElement.getChildNodes();
		for(int i = 0;i < nodes.getLength();i++){
			Node node = nodes.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element child = (Element)node;
				//process child element
			}
		}
		NodeList nodeList = rootElement.getElementsByTagName("SSCYR"); 
        if(nodeList != null) 
        { 
           for (int i = 0 ; i < nodeList.getLength(); i++) 
           { 
              Element element = (Element)nodeList.item(i); 
              String value = element.getAttribute("value");
              System.out.println(value);
           } 
        } 
	}
}
