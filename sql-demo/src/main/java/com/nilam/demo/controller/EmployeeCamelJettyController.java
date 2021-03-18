package com.nilam.demo.controller;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.Constant;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.nilam.demo.model.Employee;

@Component
public class EmployeeCamelJettyController extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration("jetty").host("localhost")
		.port("8888")
		.contextPath("api")
        // turn on json binding
        .bindingMode(RestBindingMode.json)
        // turn off binding error on empty beans
        //.dataFormatProperty("disableFeatures", "FAIL_ON_EMPTY_BEANS")
        // enable swagger api documentation
        
        
        .dataFormatProperty("prettyPrint", "true")
        .apiContextPath("api-doc")
        //.apiVendorExtension(true)
        .apiProperty("api.title", "User API")
        .apiProperty("api.version", "1.0.0")
        .apiProperty("cors", "true")
		
		
        //.apiContextPath("api-doc")
        //.apiProperty("api.version", "2.0.0")
        //.apiProperty("api.title", "Rider Auto Parts Order Services")
        //.apiProperty("api.description", "Order Service to submit orders and query status")
        //.apiProperty("api.contact.name", "Rider Auto Parts");
        // and enable CORS
       // .apiProperty("cors", "true")
        .enableCORS(false);

		Expression test = new Expression() {

			@Override
			public <T> T evaluate(Exchange exchange, Class<T> type) {
				exchange.getIn().setBody("Invalid Request");
				return null;
			};
			
		};
		// define the rest service
		 rest("/employee").consumes("application/json").produces("application/json")
		 .skipBindingOnErrorCode(false)
		 //Enable json marshalling for body in case of errors
		 // get returns List<CartDto>
		 .get().outType(Employee[].class).description("Returns all the employees")
		 .to("direct:select")
		 //.to("bean:cart?method=getItems")
        
		 .post().type(Employee.class).outType(String.class)
	     .description("Adds the employee")
	     .to("direct:insert")
	     //.to("bean:employeeRepo?method=insertEmployee")
	     
	     .put("/{id}").type(Employee.class).outType(Employee.class)
	     .description("Update employee by id")
	     .param().name("id").dataType("Integer").description("Id of item to get").endParam()
	     .to("direct:updateByID")
	     
	     .get("/{id}").outType(Employee.class).description("Return employee by id")
         .param().name("id").dataType("Integer").description("Id of item to get").endParam()
         .to("direct:selectById")
        	//.to("bean:cart?method=getItemById(${header.itemId})")
        	// get accepts CartDto
         
         .get("/v2/{name}").outType(Employee.class).description("Return employee by name")
         .param().name("name").dataType("string").description("get employee by name").endParam()
         .to("direct:selectByName")
         
        .delete("/{id}").outType(String.class).description("Removes the employee")
        .param().name("id").dataType("int").description("Id of item to remove").endParam()
        .to("direct:deleteById")
		 
		 .get("/v3/{name}").outType(Employee.class).description("Return employee by name")
         .param().name("name").dataType("string").description("get employee by name").endParam()
         .to("direct:exceptionTestings");
         
		 rest("/swagger").produces("text/html").get("/index.html").responseMessage().code(200).message("Swagger UI")
			.endResponseMessage().to("direct://get/swagger/ui/path");

		 from("direct://get/swagger/ui/path").routeId("SwaggerUI").setBody()
			.simple("resource:classpath:/swagger/index.html");
		 
		 
		  //to take query param
		 /*
		 param().name("priority").type(RestParamType.query)
		    .description("If the matter is urgent").endParam()
		*/
		
	}

}
