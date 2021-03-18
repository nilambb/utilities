package com.nilam.camel.demo.service;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.nilam.camel.demo.model.CartDto;

@Component
public class AnotherJettyEndpoint extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		 restConfiguration("jetty").port("8888").contextPath("api")
         // turn on json binding
         .bindingMode(RestBindingMode.json)
         // turn off binding error on empty beans
         .dataFormatProperty("disableFeatures", "FAIL_ON_EMPTY_BEANS")
         // enable swagger api documentation
         .apiContextPath("api-doc")
         .apiProperty("api.version", "2.0.0")
         .apiProperty("api.title", "Rider Auto Parts Order Services")
         .apiProperty("api.description",
           "Order Service to submit orders and query status")
         .apiProperty("api.contact.name", "Rider Auto Parts");
         // and enable CORS
        // .apiProperty("cors", "true")
         //.enableCORS(false);

 // define the rest service
		 rest("/cart").consumes("application/json").produces("application/json")
		 .skipBindingOnErrorCode(false) //Enable json marshalling for body in case of errors
		 // get returns List<CartDto>
		 .get().outType(CartDto[].class).description("Returns the items currently in the shopping cart")
         	.to("bean:cart?method=getItems")
         	
         .get("/{itemId}").outType(CartDto.class).description("Return item by id")
         	.param().name("itemId").dataType("Integer").description("Id of item to get").endParam()
         	.to("bean:cart?method=getItemById(${header.itemId})")
         	// get accepts CartDto
         
         .post().type(CartDto.class).outType(String.class).description("Adds the item to the shopping cart")
         	.to("bean:cart?method=addItem")
         
         .delete("/{itemId}").outType(String.class).description("Removes the item from the shopping cart")
         	.param().name("itemId").dataType("int").description("Id of item to remove").endParam()
         	.to("bean:cart?method=removeItem(${header.itemId})");
		 
		 //to take query param
		 /*
		 param().name("priority").type(RestParamType.query)
		    .description("If the matter is urgent").endParam()
		*/
	}

}
