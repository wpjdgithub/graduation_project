package Grad.Service.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
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
}
