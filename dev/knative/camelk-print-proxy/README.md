# Install the integration

kamel run --name=print-knative-proxy --property quarkus.qpid-jms.url=amqp://servicename.kubebrick.svc.cluster.local:5672 --dependency=camel-gson --dependency=camel-openapi-java --dependency=camel-amqp PrintProxyService.java 


# Test

curl -X POST -H "Content-Type: application/json" [URL_CAMELK]/request/startbatch -d "@samples/batch_blue.json"

