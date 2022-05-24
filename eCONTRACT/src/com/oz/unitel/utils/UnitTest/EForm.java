package com.oz.unitel.utils.UnitTest;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ozform")
@XmlAccessorType(XmlAccessType.FIELD)
public class EForm {
	@XmlElement(name="policyNo")
	public int policyNo;
	
	@XmlElement(name="row")
	public ArrayList<Row> row;

	public int getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(int policyNo) {
		this.policyNo = policyNo;
	}
	
	public ArrayList<Row> getRow() {
		return row;
	}
	public void setRow(ArrayList<Row> row) {
		this.row = row;
	}
}
