package com.oz.unitel.security;

import java.util.ArrayList;
import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;

import mn.unitel.websvc.LoginResponse;
import mn.unitel.websvc.ucbs.xsd.LoginResult;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.oz.unitel.ws.client.WsClient;
import com.oz.unitel.ws.client.WsConfiguration;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Resource(name="unitelWs")
	private Properties unitelWs_prop;
	
	final static Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

	private ApplicationContext context;
	
	@Override
	public JUserDetails loadUserByUsername(String user_name) {
		logger.debug("user_name :: " + user_name);
		
		context = new AnnotationConfigApplicationContext(WsConfiguration.class); 
		WsClient wsclient = context.getBean(WsClient.class); 
		
		// Get Unitel ws properties
		String wsAuth = unitelWs_prop.getProperty("ws.auth");
		int wsId = Integer.parseInt(unitelWs_prop.getProperty("ws.id"));
		
		// Get user login information via Unitel data-service
		LoginResponse response = wsclient.login(wsAuth, wsId, user_name);
		JAXBElement<LoginResult> _loginReturn = response.getReturn();	
		
		if(!_loginReturn.getValue().getRole().isNil() && !_loginReturn.getValue().getPassword().isNil()) {
			ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();		
			SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(_loginReturn.getValue().getRole().getValue().toUpperCase());
			//SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_REPORTER");
			grantedAuthorities.add(grantedAuthority);
	    	
	    	JUserDetails userDetail = new JUserDetails(user_name, _loginReturn.getValue().getPassword().getValue(), grantedAuthorities);
	    	//JUserDetails userDetail = new JUserDetails("test_2", _loginReturn.getValue().getPassword().getValue(), grantedAuthorities);
	       
	    	return userDetail;
		} else {
			throw new UsernameNotFoundException("No such user: " + user_name);
		}
	}
	
}
