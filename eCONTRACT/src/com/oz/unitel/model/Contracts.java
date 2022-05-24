package com.oz.unitel.model;

import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.oz.unitel.utils.BlobAdapter;

@Entity
@Table(name = "tb_contracts")
@XmlRootElement(name="ozform")
public class Contracts {
	
	private int id;
	private String contract_id;
	private String last_name;
	private String first_name;
	private String ssn;
	private String address;
	private String address1;
	private String email;
	private String contract_package;
	private String contract_type;
	private String product_number;
	private String contact_name;
	private String contact_number;
	private String contact_duration;
	private String contact_phone;
	private String phone_number;
	private String phone_number1;
	private Blob passport_front;
	private Blob passport_back;
	private Blob receipt1;
	private Blob receipt2;
	private Blob receipt3;
	private Blob receipt4;
	private Blob receipt5;
	private Blob receipt6;
	private String branch_name;
	private String device_name;
	private String serial_no;
	private String teller_name;
	private Blob teller_sign;
	private Blob customer_sign;
	private String contract_status;
	private String created_id;
	private Timestamp created_date;
	private String updated_id;
	private Timestamp updated_date;
	private String pdf_file_name;
	private String ozd_file_name;
	
	private String registdatemonth;
	private Blob customer_sign2;
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	
	@Column
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	@Column
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	@Column
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column
	public String getContract_package() {
		return contract_package;
	}
	public void setContract_package(String contract_package) {
		this.contract_package = contract_package;
	}
	
	@Column
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
	
	@Column
	public String getProduct_number() {
		return product_number;
	}
	public void setProduct_number(String product_number) {
		this.product_number = product_number;
	}
	
	@Column
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	
	@Column
	public String getContact_number() {
		return contact_number;
	}
	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	
	@Column
	public String getContact_duration() {
		return contact_duration;
	}
	public void setContact_duration(String contact_duration) {
		this.contact_duration = contact_duration;
	}
	
	@Column
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	
	@Column
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	@Column
	public String getPhone_number1() {
		return phone_number1;
	}
	public void setPhone_number1(String phone_number1) {
		this.phone_number1 = phone_number1;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getPassport_front() {
		return passport_front;
	}
	public void setPassport_front(Blob passport_front) {
		this.passport_front = passport_front;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getPassport_back() {
		return passport_back;
	}
	public void setPassport_back(Blob passport_back) {
		this.passport_back = passport_back;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getReceipt1() {
		return receipt1;
	}
	public void setReceipt1(Blob receipt1) {
		this.receipt1 = receipt1;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getReceipt2() {
		return receipt2;
	}
	public void setReceipt2(Blob receipt2) {
		this.receipt2 = receipt2;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getReceipt3() {
		return receipt3;
	}
	public void setReceipt3(Blob receipt3) {
		this.receipt3 = receipt3;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getReceipt4() {
		return receipt4;
	}
	public void setReceipt4(Blob receipt4) {
		this.receipt4 = receipt4;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getReceipt5() {
		return receipt5;
	}
	public void setReceipt5(Blob receipt5) {
		this.receipt5 = receipt5;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getReceipt6() {
		return receipt6;
	}
	public void setReceipt6(Blob receipt6) {
		this.receipt6 = receipt6;
	}
	
	@Column
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	
	@Column
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	
	@Column
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	
	@Column
	public String getTeller_name() {
		return teller_name;
	}
	public void setTeller_name(String teller_name) {
		this.teller_name = teller_name;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getTeller_sign() {
		return teller_sign;
	}
	public void setTeller_sign(Blob teller_sign) {
		this.teller_sign = teller_sign;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getCustomer_sign() {
		return customer_sign;
	}
	public void setCustomer_sign(Blob customer_sign) {
		this.customer_sign = customer_sign;
	}
	
	@Column
	public String getContract_status() {
		return contract_status;
	}
	public void setContract_status(String contract_status) {
		this.contract_status = contract_status;
	}
	
	@Column
	public String getCreated_id() {
		return created_id;
	}
	public void setCreated_id(String created_id) {
		this.created_id = created_id;
	}
	
	@Column
	public Timestamp getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
	
	@Column
	public String getUpdated_id() {
		return updated_id;
	}
	public void setUpdated_id(String updated_id) {
		this.updated_id = updated_id;
	}
	
	@Column
	public Timestamp getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(Timestamp updated_date) {
		this.updated_date = updated_date;
	}
	
	@Column
	public String getPdf_File_name() {
		return pdf_file_name;
	}
	public void setPdf_File_name(String pdf_file_name) {
		this.pdf_file_name = pdf_file_name;
	}
	
	@Column
	public String getOzd_File_name() {
		return ozd_file_name;
	}
	public void setOzd_File_name(String ozd_file_name) {
		this.ozd_file_name = ozd_file_name;
	}

	@Column
	public String getRegistdatemonth() {
		return registdatemonth;
	}
	public void setRegistdatemonth(String registdatemonth) {
		this.registdatemonth = registdatemonth;
	}
	
	@Column
	@XmlJavaTypeAdapter(BlobAdapter.class)
	public Blob getCustomer_sign2() {
		return customer_sign2;
	}
	public void setCustomer_sign2(Blob customer_sign2) {
		this.customer_sign2 = customer_sign2;
	}
}
