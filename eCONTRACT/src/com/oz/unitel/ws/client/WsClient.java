package com.oz.unitel.ws.client;

import javax.xml.bind.JAXBElement;

import mn.unitel.websvc.Login;
import mn.unitel.websvc.LoginResponse;
import mn.unitel.websvc.ObjectFactory;
import mn.unitel.websvc.Onlinecontract;
import mn.unitel.websvc.OnlinecontractNew;
import mn.unitel.websvc.OnlinecontractNewResponse;
import mn.unitel.websvc.OnlinecontractResponse;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


public class WsClient extends WebServiceGatewaySupport{
	
	ObjectFactory objFactory = new ObjectFactory();
	
	public LoginResponse login(String auth, Integer id, String loginUser) {
		JAXBElement<String> _auth = objFactory.createLoginAuth(auth);
		JAXBElement<String> login_user = objFactory.createLoginUser(loginUser);
		
		Login loginRequest = new Login();
		loginRequest.setAuth(_auth);
		loginRequest.setId(id);
		loginRequest.setUser(login_user);
		
		LoginResponse loginResponse = (LoginResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.unitel.mn/ucbs.php/unitel/services/Ucbs?wsdl",
						loginRequest,
						new SoapActionCallback("http://websvc.unitel.mn"));

		return loginResponse;
	}
	
	public OnlinecontractResponse getContractData(String auth, Integer id, String contractid) {
		JAXBElement<String> _auth = objFactory.createOnlinecontractAuth(auth);
		JAXBElement<String> contractidJ = objFactory.createOnlinecontractContractid(contractid);
		
		Onlinecontract onlinecontractRequest = new Onlinecontract();
		onlinecontractRequest.setAuth(_auth);
		onlinecontractRequest.setId(id);
		onlinecontractRequest.setContractid(contractidJ);
		
		OnlinecontractResponse onlinecontractResponse = (OnlinecontractResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.unitel.mn/ucbs.php/unitel/services/Ucbs?wsdl",
						onlinecontractRequest,
						new SoapActionCallback("http://websvc.unitel.mn"));
		
		return onlinecontractResponse;
	}
	
	public OnlinecontractNewResponse getContractDataNew(String auth, Integer id, String prodNo, String contractType) {
		JAXBElement<String> _auth = objFactory.createOnlinecontractAuth(auth);
		JAXBElement<String> prodnoJ = objFactory.createOnlinecontractNewProdNo(prodNo);
		JAXBElement<String> contractTypeJ = objFactory.createOnlinecontractNewContractType(contractType);
		
		OnlinecontractNew onlinecontractRequest = new OnlinecontractNew();
		onlinecontractRequest.setAuth(_auth);
		onlinecontractRequest.setId(id);
		onlinecontractRequest.setProdNo(prodnoJ);
		onlinecontractRequest.setContractType(contractTypeJ);
		
		OnlinecontractNewResponse onlinecontractResponse = (OnlinecontractNewResponse) getWebServiceTemplate()
				.marshalSendAndReceive("https://www.unitel.mn/ucbs.php/unitel/services/Ucbs?wsdl",
						onlinecontractRequest,
						new SoapActionCallback("http://websvc.unitel.mn"));
		
		return onlinecontractResponse;
	}
	
}
