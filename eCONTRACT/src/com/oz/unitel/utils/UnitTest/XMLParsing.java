package com.oz.unitel.utils.UnitTest;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XMLParsing {
	public static void main(String[] args) {
		File _xml_file = new File("D://test.txt");
    	JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(EForm.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    	
	    	EForm eForm = (EForm) jaxbUnmarshaller.unmarshal(_xml_file);
	    	
	    	System.out.println("Size :: " + eForm.getRow().size());
	    	System.out.println("Email :: " + eForm.getRow().get(0).getEmail());
	    	System.out.println("Email :: " + eForm.getRow().get(1).getEmail());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
