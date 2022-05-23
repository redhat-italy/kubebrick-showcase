package org.kubebrick;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/request")
public class PrintRequestResource {

    Logger log = Logger.getLogger(PrintRequestResource.class.getName());

    @Inject
    PrintStarter ps;


    @POST
    @Path("/startbatch")
    @Produces(MediaType.APPLICATION_JSON)
    public String send(String batch) {
        log.log(Level.INFO,"batch: \n" + batch);
        ps.sendPrint(batch);
        return "{\"result\":\"print requested\"}";
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test() {
        String batch = "{\n" +
                "    \"orders\": [\n" +
                "        {\n" +
                "            \"type\": \"square\",\n" +
                "            \"color\": \"blue\",\n" +
                "            \"timestamp\": \"20220329480204\",\n" +
                "            \"batchid\": \"TEST\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"square\",\n" +
                "            \"color\": \"blue\",\n" +
                "            \"timestamp\": \"20220329480204\",\n" +
                "            \"batchid\": \"TEST\"\n" +
                "        }\n" +
                "        ,\n" +
                "        {\n" +
                "            \"type\": \"square\",\n" +
                "            \"color\": \"blue\",\n" +
                "            \"timestamp\": \"20220329480204\",\n" +
                "            \"batchid\": \"TEST\"\n" +
                "        }\n" +
                "        ,\n" +
                "        {\n" +
                "            \"type\": \"square\",\n" +
                "            \"color\": \"blue\",\n" +
                "            \"timestamp\": \"20220329480204\",\n" +
                "            \"batchid\": \"TEST\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        ps.sendPrint(batch);
        return "{\"result\":\"print requested\"}";
    }
}