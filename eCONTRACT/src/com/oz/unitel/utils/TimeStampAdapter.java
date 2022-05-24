package com.oz.unitel.utils;

import java.sql.Timestamp;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeStampAdapter extends XmlAdapter<String, Timestamp>{

	@Override
	public String marshal(Timestamp arg0) throws Exception {
		// TODO Auto-generated method stub
		return arg0.toString();
	}

	@Override
	public Timestamp unmarshal(String arg0) throws Exception {
		// TODO Auto-generated method stub
		return new Timestamp(Long.parseLong(arg0));
	}

}
