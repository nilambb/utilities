package com.nilam.demo.swagger.ui;

import org.apache.camel.builder.RouteBuilder;


public class SwaggerConfig extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		rest("/swagger").produces("text/html").get("/index.html").responseMessage().code(200).message("Swagger UI")
				.endResponseMessage().to("direct://get/swagger/ui/path");

		from("direct://get/swagger/ui/path").routeId("SwaggerUI").setBody()
				.simple("resource:classpath:/swagger/index.html");

	}

}
