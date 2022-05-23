package org.kubebrick;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@ApplicationScoped
public class PrintStarter {

    @Inject
    ConnectionFactory connectionFactory;

    public void sendPrint(String piecesBatch) {
        try {
            JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);

            Queue printRequest = context.createQueue("PrintRequest");
            context.createProducer().send(printRequest, piecesBatch);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}