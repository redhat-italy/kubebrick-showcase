import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class PrintProxyService extends org.apache.camel.builder.RouteBuilder {

    @Override
    public void configure() throws Exception {
        // @formatter:off
        restConfiguration()
          .contextPath("request")
          .bindingMode(RestBindingMode.off)
          .jsonDataFormat("json-gson")
          .dataFormatProperty("prettyPrint", "true")
          .enableCORS(true);

        rest("/")
          .consumes("application/json")
          .post("/startbatch")         
            .to("direct:startbatch");

        from("direct:startbatch")
            .log(">> body ${body}")            
            //PrintRequest
            .to("amqp:queue:PrintRequest?exchangePattern=InOnly")
            .setBody().simple("{\"result\":\"print requested\"}");

    }
     
}