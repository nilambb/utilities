package com.nilam.demo.websocket;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.component.websocket.WebsocketConstants;
import org.springframework.stereotype.Component;

import com.nilam.demo.model.Employee;

//@Component
public class WebsocketRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		/*
		 * String uri =
		 * "websocket://127.0.0.1:8443/test?sslContextParameters=#sslContextParameters";
		 * 
		 * from(uri).log(">>> Message received from WebSocket Client : ${body}").to(
		 * "mock:client").loop(10).setBody() .constant(">> Welcome on board!").to(uri);
		 */
		
		rest("/webSocket").consumes("application/json").produces("application/json")
		 .skipBindingOnErrorCode(false)
		 //Enable json marshalling for body in case of errors
		 // get returns List<CartDto>
		 //.get().outType(String.class).description("Returns the messages from websocket")
		 //.to("direct:Producer1")
		 //.to("bean:cart?method=getItems")
       
		 .post().type(String.class).outType(String.class)
	     .description("put and get messages from websocket")
	     .to("direct:Producer1").to("direct:Consumer1");
	     //.to("bean:employeeRepo?method=insertEmployee")
	     
		
		 from("direct:Producer1").
	      //we will use this connectionKey for uniquely identifying each connection from the client.
	      //setHeader(WebsocketConstants.CONNECTION_KEY, header("connectionKey")).
	      to("websocket://localhost:8999/camel-websocket?sendToAll=false").end();
		 
		   from("direct:Consumer1")
		    .process(new Processor() {
		     public void process(Exchange exchange) throws Exception {
		       Map<String, Object> headers=exchange.getIn().getHeaders();
		    //you can get a unique connection key from the exchange header.This would be unique for each client.
		    //store this key somewhere, to send messages to particular client.
		    String uniqueConnectionKey=headers.get("websocket.connectionKey").toString();
		          //you can get message from the client like below.
		          String dataFromClient=exchange.getIn().getBody().toString();
		          exchange.getIn().setBody("The message received is = " + dataFromClient);
		 
		   }
		}).end();
	}

}
