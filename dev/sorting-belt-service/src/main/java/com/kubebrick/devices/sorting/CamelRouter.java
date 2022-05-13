package com.kubebrick.devices.sorting;

import javax.inject.Inject;
//import javax.jms.ConnectionFactory;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * A simple Camel REST DSL route that implements the greetings service.
 * 
 */
public class CamelRouter extends RouteBuilder {
  /*  @Inject
    ConnectionFactory connectionFactory;*/

    @Override
    public void configure() throws Exception {
        // @formatter:off
    
        from("amqp:queue:SortingBelt")
            //.log("${body}")
            .unmarshal().json(JsonLibrary.Gson)
            //.unmarshal().string("UTF-8")
            .choice()
                .when().jsonpath("$[?(@.color == 'blue')]" , false)
                    .log("Received one blue")                      
                    .marshal().json(JsonLibrary.Gson)                    
                    .setHeader("blue").body()
                    .to("amqp:queue:bluepieces?exchangePattern=InOnly")
                .when().jsonpath("$[?(@.color == 'green')]" , false)
                    .log("Received one green")
                    .marshal().json(JsonLibrary.Gson)
                    .setHeader("green").body()
                    .to("amqp:queue:greenpieces?exchangePattern=InOnly")
                .when().jsonpath("$[?(@.color == 'red')]" , false)
                    .log("Received one red")
                    .marshal().json(JsonLibrary.Gson)
                    .setHeader("red").body()
                    .to("amqp:queue:redpieces?exchangePattern=InOnly")     
                .when().jsonpath("$[?(@.color == 'white')]" , false)
                    .log("Received one white")
                    .marshal().json(JsonLibrary.Gson)
                    .setHeader("white").body()
                    .to("amqp:queue:whitepieces?exchangePattern=InOnly")                                       
                .otherwise()
                .log("Unknown color > /dev/null").stop()
            .end();
            //.to("stomp:queue:SortingBelt?brokerURL={{broker.url}}");
 
            // @formatter:on
    }
}