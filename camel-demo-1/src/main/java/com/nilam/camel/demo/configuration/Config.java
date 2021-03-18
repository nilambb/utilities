package com.nilam.camel.demo.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.component.swagger.DefaultCamelSwaggerServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nilam.camel.demo.model.CartDto;

@Configuration
public class Config {
	
	 @Bean
	  public ServletRegistrationBean camelServletRegistrationBean() {
	    ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/camel/*");
	    registration.setName("CamelServlet");
	    return registration;
	  }
	 
	 @Bean(name = "cart")
	  public CartDto cartDto() {
	        return new CartDto();
	  }
	 
	 @Bean
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
	 
	  @Controller
	    class SwaggerWelcome {
	        @RequestMapping("/swagger-ui")
	        public String redirectToUi() {
	            return "redirect:/webjars/swagger-ui/index.html?url=/api/swagger&validatorUrl=";
	        }
	    }
}
