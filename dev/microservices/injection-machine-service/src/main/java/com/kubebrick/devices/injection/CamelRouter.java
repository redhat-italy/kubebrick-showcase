package com.kubebrick.devices.injection;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * A simple Camel REST DSL for Injection Machine
 */
public class CamelRouter extends RouteBuilder {

  private int base_temperature = 180;

  @Override
  public void configure() throws Exception {

    // @formatter:off

          from("amqp:queue:PrintRequest?exchangePattern=InOnly")
            .unmarshal().json(JsonLibrary.Gson)
            .to("direct:startbatch");
    
          from("direct:startbatch")
            .log(">> body ${body}")
            .to("direct:splitbatch")
            .setBody().simple("{\"result\":\"print requested\"}");
                  
          from("direct:splitbatch")
            .split().jsonpath("$.orders")
              .streaming()
              .log("batch >> ${body}")
              .marshal().json(JsonLibrary.Gson)        
              .to("amqp:queue:SortingBelt?exchangePattern=InOnly");
   







        restConfiguration()
              .contextPath("camel")
              .bindingMode(RestBindingMode.off)
              .jsonDataFormat("json-gson")
              .dataFormatProperty("prettyPrint", "true")
              .enableCORS(true);

            rest("/")
              .get("/temperature")
                .to("direct:temperature")          
              .consumes("application/json")
              .post("/startbatch")         
                .to("direct:startbatch");

        from("direct:temperature")
          .process(
          // @formatter:on
            new Processor() {
              
              @Override
              public void process(Exchange exchange) throws Exception {
                int delta = getDelta();                
                int temperature = base_temperature + delta;
                exchange.getMessage().setBody("{\"temperature\":\"" + temperature + "\"}");
              }

              private int getDelta() {
                int max = 15;
                int min = 0;
                int delta = new Random().nextInt(max - min + 1) + min;
                return delta;
              }
          // @formatter:off
        });        
  
  }
}