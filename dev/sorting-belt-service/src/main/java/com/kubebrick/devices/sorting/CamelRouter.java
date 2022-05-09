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
                      .log("One blue")
                      .marshal().json(JsonLibrary.Gson)                    
                      .to("amqp:queue:bluepieces?exchangePattern=InOnly")
                  .when().jsonpath("$[?(@.color == 'green')]" , false)
                      .log("One green")
                      .marshal().json(JsonLibrary.Gson)
                      .to("amqp:queue:greenpieces?exchangePattern=InOnly")
                  .otherwise()
                  .log("Last resort").stop()
                 .end();
            //.to("stomp:queue:SortingBelt?brokerURL={{broker.url}}");
 
            // @formatter:on
    }
}