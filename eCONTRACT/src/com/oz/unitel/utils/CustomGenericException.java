package com.oz.unitel.utils;

public class CustomGenericException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 555695261058993988L;
	
	private String errMsg;
 
	public String getErrMsg() {
		return errMsg;
	}
 
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
 
	public CustomGenericException(String errMsg) {
		this.errMsg = errMsg;
	}
}
