package com.nilam.camel.demo.service;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.helpers.Transform;

//@Component
public class MySimpleCamelRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		/*
		 * restConfiguration().component("servlet").bindingMode(RestBindingMode.json);
		 * 
		 * rest().get("/hello").to("direct:hello");
		 * 
		 * from("direct:hello").log(LoggingLevel.INFO,
		 * "Hello World").transform().simple("Hello World");
		 */
		/*
		 * from("jetty://http://localhost:8888/greeting") .log("Received a request")
		 * .setBody(simple("Hello, world!"));
		 */

		restConfiguration().component("jetty").bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true").port(8888);

		rest().get("/greet").to("direct:greet");
		
		from("direct:greet").log("Http jetty Route started").setHeader(Exchange.HTTP_METHOD, simple("GET"))
				.setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
				//.to("jetty:http://localhost:8999/")
				.transform().simple("Hello World");
	}

}
