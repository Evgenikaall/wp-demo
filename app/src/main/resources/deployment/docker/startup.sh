#!/bin/bash

echo "Starting kafka-example"
podman compose up -d

echo "Waiting 15s..."
sleep 15

echo "Trying to create person-topic"
podman container exec kafka-broker-1 kafka-topics --create --topic person-topic --bootstrap-server kafka-broker-1:9092 --replication-factor 1 --partitions 1
podman container exec kafka-broker-1 kafka-topics --create --topic order-topic --bootstrap-server kafka-broker-1:9092 --replication-factor 1 --partitions 1

sleep 10

register_schema() {
  local VALUE=$1
  local SCHEMA_REGISTRY_URL="http://localhost:8081"
  local SCHEMA_FILE="../schemas/common/${VALUE}/${VALUE}.avsc"
  local SUBJECT="${VALUE}-topic-value"

  # Ensure schema file exists
  if [ ! -f "$SCHEMA_FILE" ]; then
    echo "Schema file not found: $SCHEMA_FILE"
    return 1
  fi

  # Read and format the schema file as a JSON string
  local SCHEMA=$(cat "$SCHEMA_FILE" | tr -d '\n' | sed 's/"/\\"/g')

  # Prepare the JSON payload
  local PAYLOAD="{\"schema\": \"${SCHEMA}\"}"

  # Print payload for debugging
  echo "Payload: ${PAYLOAD}"

  # Attempt to register schema up to 10 times
  for i in {1..10}; do
    local RESPONSE=$(curl -s -H "Content-Type: application/vnd.schemaregistry.v1+json" --data "$PAYLOAD" -X POST $SCHEMA_REGISTRY_URL/subjects/$SUBJECT/versions)

    if echo "$RESPONSE" | grep -q '"id"'; then
      echo "Schema registered successfully"
      echo "Response: $RESPONSE"
      return 0
    else
      echo "Failed to register schema, attempt $i/10"
      echo "Response: $RESPONSE"
      sleep 5
    fi
  done

  # Final check if schema registration was successful after all attempts
  if ! echo "$RESPONSE" | grep -q '"id"'; then
    echo "Schema registration failed after 10 attempts"
    return 1
  fi
}

register_schema "person"
register_schema "order"
