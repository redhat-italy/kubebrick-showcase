package com.kubebrick.devices.sorting;

import javax.inject.Inject;
//import javax.jms.ConnectionFactory;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
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
    
        from("stomp:queue:SortingBelt")
            .log("${body}");
            //.to("stomp:queue:SortingBelt?brokerURL={{broker.url}}");
 
            // @formatter:on
    }
}