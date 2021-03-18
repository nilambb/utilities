package com.nilam.camel.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCamelApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCamelApplication.class, args);

	}

	/*
	 * @Bean public ServletRegistrationBean camelServletRegistrationBean() {
	 * ServletRegistrationBean registration = new ServletRegistrationBean(new
	 * CamelHttpTransportServlet(), "/camel/*");
	 * registration.setName("CamelServlet"); return registration; }
	 */
}
