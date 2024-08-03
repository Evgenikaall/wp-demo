#!/bin/bash

echo "Starting kafka-example"
podman compose up -d

echo "Waiting 15s..."
sleep 15

echo "Trying to create person-topic"
podman container exec kafka-broker-1 kafka-topics --create --topic person-topic --bootstrap-server kafka-broker-1:9092 --replication-factor 1 --partitions 1

sleep 10

# Variables
SCHEMA_REGISTRY_URL="http://localhost:8081"
SCHEMA_FILE="../schemas/common/person/person.avsc"
SUBJECT="person-topic-value"

# Ensure schema file exists
if [ ! -f "$SCHEMA_FILE" ]; then
  echo "Schema file not found: $SCHEMA_FILE"
  exit 1
fi

# Read and format the schema file as a JSON string
SCHEMA=$(cat "$SCHEMA_FILE" | tr -d '\n' | sed 's/"/\\"/g')

# Prepare the JSON payload
PAYLOAD="{\"schema\": \"${SCHEMA}\"}"

# Print payload for debugging
echo "Payload: ${PAYLOAD}"

# Attempt to register schema up to 10 times
for i in {1..10}; do
  RESPONSE=$(curl -s -H "Content-Type: application/vnd.schemaregistry.v1+json" --data "$PAYLOAD" -X POST $SCHEMA_REGISTRY_URL/subjects/$SUBJECT/versions)

  if echo "$RESPONSE" | grep -q '"id"'; then
    echo "Schema registered successfully"
    echo "Response: $RESPONSE"
    break
  else
    echo "Failed to register schema, attempt $i/10"
    echo "Response: $RESPONSE"
    sleep 5
  fi
done

# Final check if schema registration was successful after all attempts
if ! echo "$RESPONSE" | grep -q '"id"'; then
  echo "Schema registration failed after 10 attempts"
  exit 1
fi
