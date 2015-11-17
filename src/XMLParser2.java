import java.io.IOException;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLParser2 {
	
	String filePath = "";
	
	public XMLParser2(String filePath) {
		this.filePath = filePath;
	}
	
	public HashSet<String> getStopwords(){
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try{
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			XMLHandler1 xmHandler = new XMLHandler1();
			
			//parse the file and also register this class for call backs
			sp.parse(filePath, xmHandler);
			return xmHandler.stopwords;
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}

class XMLHandler1 extends DefaultHandler{
	String tempVal ;
	
	HashSet<String> stopwords = new HashSet<String>(); 
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if(qName.equalsIgnoreCase("skos:prefLabel") | qName.equalsIgnoreCase("skos:altLabel")){
			String[] tokens = tempVal.split(" ");
			for(String token: tokens){
				stopwords.add(token);
			}
		}
	}
	
}
