#!/bin/bash

echo "Starting kafka-example"
podman-compose up -d

echo "Waiting 15s..."
sleep 15

echo "Trying to create person-topic"
podman container exec kafka-broker-1 kafka-topics --create --topic person-topic --bootstrap-server kafka-broker-1:9092 --replication-factor 1 --partitions 1

sleep 10

SCHEMA=$(cat ../schemas/common/person/person.avsc | sed 's|\"|\\"|g')
SCHEMA_REGISTRY_URL="http://localhost:8081"
for i in {1..10}; do
  RESPONSE=$(curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" --data '{"schema": '"$SCHEMA"'}' $SCHEMA_REGISTRY_URL/subjects/person-topic-value/versions)
  if echo "$RESPONSE" | grep -q '"id"'; then
    echo "Schema registered successfully"
    break
  else
    echo "Failed to register schema, attempt $i/10"
    sleep 5
  fi
done