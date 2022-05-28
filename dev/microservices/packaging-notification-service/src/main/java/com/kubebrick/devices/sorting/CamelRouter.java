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

    private final String route_name = "Pack Notification";

    @Override
    public void configure() throws Exception {

        // @formatter:off
        from("amqp:queue:packagingnotification").id(route_name)  
        .unmarshal().json(JsonLibrary.Gson)    
            .log("Message ${body}")
            .setHeader("batchsize", jsonpath(".batchsize"))
            .setHeader("creationtime", jsonpath(".creationtime"))
            .setHeader("queuename", jsonpath(".queuename"))            
            .to("sql:INSERT INTO packagelog ( color, creationtimestamp, pieces) VALUES (:#queuename, TO_TIMESTAMP(:#creationtime, 'YYYYMMDD HH24:MI SS'), CAST(:#batchsize AS DECIMAL ))")
        .end();
        // @formatter:on
    }

}