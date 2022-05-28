package com.kubebrick.devices.sorting;

import javax.inject.Inject;
//import javax.jms.ConnectionFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.FlexibleAggregationStrategy;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.apache.camel.builder.AggregationStrategies;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

public class CamelRouter extends RouteBuilder {

    private final String route_name = "Pieces aggregation";

    @Override
    public void configure() throws Exception {

        // @formatter:off

        from("amqp:queue:{{queue}}").id(route_name)
            .aggregate( AggregationStrategies.groupedExchange()).constant(false)
                .completionSize("{{pack.size}}")
                .completionTimeout("{{aggregation.timeout}}")
            .log("Message ${body}")
            .process(new PackDetailProcessor())
            .setHeader("batchsize", simple("${body.size}"))
            .setHeader("creationtime", simple("${date:now}"))
            .setHeader("queuename", simple("{{queue}}"))
            .setBody(simple("{\"batchsize\":\"${body.size}\", \"creationtime\":\"${date:now:YYYYMMdd HH:mm:ss SSS}\", \"queuename\":\"{{queue}}\"}"))
            .to("amqp:queue:packagingnotification?exchangePattern=InOnly") 
        .end();
        
        // @formatter:on
    }

    private class PackDetailProcessor implements Processor {

        private Logger logger = Logger.getLogger(route_name);

        @Override
        public void process(Exchange exchange) throws Exception {

            List items = (List) exchange.getMessage().getBody();

            StringBuilder sb = new StringBuilder();
            sb.append("\n    Items in the pack [" + items.size() + "]:\n");

            for (Object o : items) {
                byte[] anItem = (byte[]) ((Exchange) o).getMessage().getBody();
                sb.append("        " + new String(anItem) + "\n");
            }

            logger.log(Level.INFO, sb.toString());
        }
    }

}