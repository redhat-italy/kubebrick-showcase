package com.kubebrick.devices.sorting;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

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