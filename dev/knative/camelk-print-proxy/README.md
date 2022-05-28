# Install the integration

kamel run --name=print-knative-proxy --property quarkus.qpid-jms.url=amqp://servicename.kubebrick.svc.cluster.local:5672 --dependency=camel-gson --dependency=camel-openapi-java --dependency=camel-amqp PrintProxyService.java 

# Install for SSL connection to external AMQ

```kamel run --name=print-knative-proxy \
-p quarkus.http.cors=true \
--resource file:<PATH_TO_YOUR_TRUST_STORE> \
-t jvm.enabled=true \
-t jvm.options=-Djavax.net.ssl.trustStore=/etc/camel/resources/<YOUR_TRUST_STORE_FILE_NAME> \
-t jvm.options=-Djavax.net.ssl.trustStorePassword=<YOUR_TRUST_STORE_PWD>  \
--property quarkus.qpid-jms.url=amqps://<AMQ_URL>:443?sslEnabled=true \
--dependency=camel-gson --dependency=camel-openapi-java --dependency=camel-amqp \
PrintProxyService.java
```

All the files passed with ```--resource file:<PATH_TO_FILE>``` will go on the path  ```/etc/camel/resources/``` in the Camel-K runtime container

# Test

curl -X POST -H "Content-Type: application/json" URL_CAMELK_INTEGRATION/request/startbatch -d "@samples/batch_blue.json"

