package com.nilam.camel.demo.service;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JettyEndpoint extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("jetty:http://localhost:8090/api/v1/greet")
		//.setBody().constant("Passssssssss").end();
		.setBody(simple("Hello, world!")).end();

	}

}
