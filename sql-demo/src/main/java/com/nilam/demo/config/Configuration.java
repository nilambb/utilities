package com.nilam.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.swagger.DefaultCamelSwaggerServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.nilam.demo.model.Employee;

@org.springframework.context.annotation.Configuration
public class Configuration {
	@Bean(name = "employee")
	public Employee getEmployee() {
		return new Employee();
	}

	//@Bean
	public ServletRegistrationBean swaggerServlet() {
		ServletRegistrationBean swagger = new ServletRegistrationBean(new DefaultCamelSwaggerServlet(), "/api-doc/*");
		Map<String, String> params = new HashMap<>();
		params.put("base.path", "api");
		params.put("api.title", "my api title");
		params.put("api.description", "my api description");
		params.put("api.termsOfServiceUrl", "termsOfServiceUrl");
		params.put("api.license", "license");
		params.put("api.licenseUrl", "licenseUrl");
		swagger.setInitParameters(params);
		return swagger;
	}

}
