package com.oz.unitel.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * @author PHAM
 *
 */
public class StringConverterUtils {
	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String convertDateToString(Date date, String dateFormat) {
		DateFormat sdf = new SimpleDateFormat(dateFormat);
	    String strDate = sdf.format(date);
	    return strDate;
	}
	
	/**
	 * 
	 * @param strDate
	 * @param dateFormat
	 * @return
	 */
	public static Date convertStringToDate(String strDate, String dateFormat) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 
	 * @param xmlSource
	 * @param xmlFilePath
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static void stringToDom(String xmlSource, String xmlFilePath) throws SAXException, ParserConfigurationException, IOException, TransformerException{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));
	    
	    // Use a Transformer for output
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer();

	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(new File(xmlFilePath).getPath());
	    transformer.transform(source, result);
	}  
}
