package com.kubebrick.devices.sorting;

import javax.inject.Inject;
//import javax.jms.ConnectionFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.FlexibleAggregationStrategy;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.apache.camel.builder.AggregationStrategies;

import java.util.List;

/**
 * A simple Camel REST DSL route that implements the greetings service.
 */
public class CamelRouter extends RouteBuilder {
  /*  @Inject
    ConnectionFactory connectionFactory;*/

    @Override
    public void configure() throws Exception {
        // @formatter:off
        from("amqp:queue:{{queue}}")
            .aggregate( AggregationStrategies.groupedExchange()).constant(false)
                .completionSize(5)
                .completionTimeout(1000)
            .log("Message ${body}")
            .process(new PackDetailProcessor())
        .end();
        //.to("stomp:queue:SortingBelt?brokerURL={{broker.url}}");
 
        // @formatter:on
    }

    private class PackDetailProcessor implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            List items = (List) exchange.getMessage().getBody();
            for (Object o : items) {
                System.out.println(o);
                Exchange ex = (Exchange) o;
                byte[] body = (byte[]) ex.getMessage().getBody();
                System.out.println("Item in the pack > " + new String(body));
            }

        }
    }





     /*
    private class MyAggregationStrategy implements org.apache.camel.AggregationStrategy {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            return null;
        }
    }   */
}