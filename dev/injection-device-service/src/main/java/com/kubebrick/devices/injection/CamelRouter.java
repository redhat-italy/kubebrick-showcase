package com.kubebrick.devices.injection;

import javax.inject.Inject;
//import javax.jms.ConnectionFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * A simple Camel REST DSL route that implements the greetings service.
 */
public class CamelRouter extends RouteBuilder {
    /*
     * @Inject
     * ConnectionFactory connectionFactory;
     */

    @Override
    public void configure() throws Exception {

        Processor processor = new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                System.out.println(exchange.toString());
            }
        };

        // @formatter:off
        restConfiguration()
          .contextPath("camel")
          .bindingMode(RestBindingMode.off)
                .jsonDataFormat("json-gson")

                //.jsonDataFormat("json-gson")
          .dataFormatProperty("prettyPrint", "true")
          .enableCORS(true);


        rest("/")
          .get("/hello").to("direct:hello")
          //.get("/bye").consumes("application/json").to("direct:bye")
          .consumes("application/json").post("/startbatch").to("direct:startbatch")
          ;

        from("direct:hello")
          .transform().constant("Hello World");

      from("direct:startbatch")
          .log(">> body ${body}")
          .split().jsonpath("$.orders")
          .streaming()
          //.aggregate(AggregationStrategies.string())
          //.constant("true")
          //.completionSize(1)
          //.completionTimeout(1000)
          .log("batch >> ${body}")
                .process(processor)
              .marshal().json(JsonLibrary.Gson)
          //.to("stomp:queue:SortingBelt?brokerURL={{broker.url}}");
          //.to("amqp:queue:SortingBelt?brokerURL={{broker.url}}");
          .to("amqp:queue:SortingBelt?exchangePattern=InOnly");

//          .transform().constant("Bye World");


        /*
        from("timer:foo?period=1000")
            //.setBody(simple("{\"test\":\"${date:now:yyyyMMddssSSSS}\"}"))
            .setBody(simple("{\"type\":\"square\",\"color\":\"blue\",\"timestamp\":\"${date:now:yyyyMMddssSSSS}\",\"batchid\":\"12345\"}"))
            .to("stomp:queue:SortingBelt?brokerURL={{broker.url}}");
           // .to("amqp:queue:SortingBelt");
           */
        // @formatter:on

    }
}