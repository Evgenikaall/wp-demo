#!/bin/bash

set -e

# Function to wait for a Strimzi Kafka custom resource to be ready
wait_for_kafka() {
  local namespace=$1
  local kafka=$2

  echo "Waiting for Kafka custom resource $kafka in namespace $namespace to be ready..."
  for i in {1..22}; do
    local status=$(oc get kafkas/"$kafka" --namespace="$namespace" -o jsonpath='{.status.conditions[?(@.type=="Ready")].status}')
    if [ "$status" == "True" ]; then
      echo "Kafka custom resource $kafka is now ready."
      return 0
    fi
    echo "Kafka custom resource $kafka is not ready yet. Retrying in 30 seconds..."
    sleep 30
  done
  echo "Kafka custom resource $kafka did not become ready in time."
  exit 1
}


# Function to wait for a KafkaTopic custom resource to be ready
wait_for_kafka_topic() {
  local namespace=$1
  local topic=$2

  echo "Waiting for KafkaTopic custom resource $topic in namespace $namespace to be ready..."
  for i in {1..12}; do
    local status=$(oc get kafkatopics/"$topic" --namespace="$namespace" -o jsonpath='{.status.conditions[?(@.type=="Ready")].status}')
    if [ "$status" == "True" ]; then
      echo "KafkaTopic custom resource $topic is now ready."
      return 0
    fi
    echo "KafkaTopic custom resource $topic is not ready yet. Retrying in 30 seconds..."
    sleep 30
  done
  echo "KafkaTopic custom resource $topic did not become ready in time."
  exit 1
}

# Function to wait for a deployment to be ready
wait_for_deployment() {
  local namespace=$1
  local deployment=$2

  echo "Waiting for deployment $deployment in namespace $namespace to be ready..."
  oc wait --namespace="$namespace" --for=condition=available deployment/"$deployment" --timeout=600s
}

# Function to wait for a route to be accessible
wait_for_route() {
  local namespace=$1
  local route=$2

  echo "Waiting for route $route in namespace $namespace to be accessible..."
  local url="http://$(oc get route --namespace="$namespace" "$route" -o jsonpath='{.spec.host}')"
  for i in {1..60}; do
    if curl -s --head --request GET "$url" | grep "200 OK" > /dev/null; then
      echo "Route $route is accessible!"
      return 0
    fi
    echo "Route $route not accessible yet. Retrying in 10 seconds..."
    sleep 10
  done
  echo "Route $route did not become accessible in time."
  exit 1
}

setup_schema_registry() {
  SCHEMA=$(cat ../schemas/common/person/person.avsc | sed 's|\"|\\"|g')
  local url="http://$(oc get routes schema-registry -o=jsonpath={.spec.host})/subjects/person-topic-value/versions"
  for i in {1..60}; do
    if curl -s --head --request GET "$url" | grep "200 OK" > /dev/null; then
    echo "person-topic is registered in schema registry"
      return 0
    fi
    echo "Retrying to register person-topic in schema registry"


    curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" --data "$schema" "$url"

    sleep 10
  done
  echo "person-topic is not registered in schema registry"
  exit 1
}


# Apply Kafka cluster YAML and wait
oc apply -f kafka-cluster.yml
wait_for_kafka kafka-example kafka-cluster

# Apply KafkaTopic YAML and wait
oc apply -f kafka-person-topic.yml
wait_for_kafka_topic kafka-example person-topic

# Apply Kafka UI deployment, service, and route YAML and wait
oc apply -f kafka-ui-deployment.yml
oc apply -f kafka-ui-service.yml
oc apply -f kafka-ui-route.yml
wait_for_deployment kafka-example kafka-ui
wait_for_route kafka-example kafka-ui

# Apply Schema Registry deployment, service, and route YAML and wait
oc apply -f schema-registry-deployment.yml
oc apply -f schema-registry-service.yml
oc apply -f schema-registry-route.yml
wait_for_deployment kafka-example schema-registry
wait_for_route kafka-example schema-registry

setup_schema_registry


echo "All resources have been deployed and are ready."
