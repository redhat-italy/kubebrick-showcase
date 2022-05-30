export ROUTE=<OCP Route AMQ Stream Http Bridge>
# Create Consumer
curl -X POST $ROUTE/consumers/brick-group \
  -H 'content-type: application/vnd.kafka.v2+json' \
  -d '{
    "name": "brick-http-consumer",
    "format": "json",
    "auto.offset.reset": "earliest",
    "enable.auto.commit": false
  }'


# Subscribe Consumer
curl -X POST $ROUTE/consumers/brick-group/instances/brick-http-consumer/subscription \
  -H 'content-type: application/vnd.kafka.v2+json' \
  -d '{
    "topics": [
        "kubebrick.public.packagelog"
    ]
}'

# Consuming
curl $ROUTE/consumers/brick-group/instances/brick-http-consumer/records \
  -H 'accept: application/vnd.kafka.json.v2+json'

# Delete Consumer
curl -X DELETE $ROUTE/consumers/brick-group/instances/brick-http-consumer