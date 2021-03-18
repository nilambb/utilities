package com.nilam.demo.service.exception;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		onException(Exception.class)
		.setBody(simple("Invalid Request.......")).end();
		
	}

}
