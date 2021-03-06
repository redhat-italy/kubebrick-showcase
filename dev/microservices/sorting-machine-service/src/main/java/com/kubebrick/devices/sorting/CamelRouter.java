package com.kubebrick.devices.sorting;

import javax.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // @formatter:off
    
        from("amqp:queue:SortingBelt")            
            .unmarshal().json(JsonLibrary.Gson)
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

            // @formatter:on
    }
}