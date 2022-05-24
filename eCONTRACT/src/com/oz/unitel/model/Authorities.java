package com.oz.unitel.model;

import java.io.Serializable;

public class Authorities implements Serializable{
	private static final long serialVersionUID = 6414326229582193770L;
	private String userName;
	private String userRole;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
