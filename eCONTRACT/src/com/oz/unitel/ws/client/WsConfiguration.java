package com.oz.unitel.ws.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration 
public class WsConfiguration {
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("mn.unitel.websvc");
		return marshaller;
	}

	@Bean
	public WsClient wsClient() {
		WsClient client = new WsClient();
		client.setDefaultUri("http://websvc.unitel.mn");
		Jaxb2Marshaller marshaller = marshaller();
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
