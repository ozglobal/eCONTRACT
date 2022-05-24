package com.oz.unitel.utils;

import java.nio.charset.Charset;
import java.sql.Blob;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BlobAdapter extends XmlAdapter<String, Blob>{

	@Override
	public String marshal(Blob arg0) throws Exception {
		byte[] bdata = arg0.getBytes(1, (int) arg0.length());
		String str = new String(bdata);
		return str;
	}

	@Override
	public Blob unmarshal(String arg0) throws Exception {
		Blob blob=null; 
		blob=new SerialBlob(arg0.getBytes(Charset.forName("UTF-8")));
		return blob;
	}
	
}