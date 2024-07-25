#!/bin/bash

set -e

# Function to delete Kafka resources
delete_kafka_resources() {
  local namespace=$1
  local kafka=$2
  local topic=$3

  echo "Deleting Kafka custom resource $kafka in namespace $namespace..."
  oc delete kafkas/"$kafka" --namespace="$namespace"

  echo "Deleting KafkaTopic custom resource $topic in namespace $namespace..."
  oc delete kafkatopics/"$topic" --namespace="$namespace"
}

# Function to delete deployments, services, and routes
delete_deployment_and_services() {
  local namespace=$1
  local deployment=$2
  local service=$3
  local route=$4

  echo "Deleting deployment $deployment in namespace $namespace..."
  oc delete deployment/"$deployment" --namespace="$namespace"

  echo "Deleting service $service in namespace $namespace..."
  oc delete service/"$service" --namespace="$namespace"

  echo "Deleting route $route in namespace $namespace..."
  oc delete route/"$route" --namespace="$namespace"
}

# Function to delete all resources in a namespace
delete_all_resources() {
  local namespace=$1

  echo "Deleting all resources in namespace $namespace..."
  oc delete all --all --namespace="$namespace"

  oc delete configmaps --all --namespace="$namespace"
  oc delete persistentvolumeclaims --all --namespace="$namespace"
  oc delete secrets --all --namespace="$namespace"
  oc delete statefulsets --all --namespace="$namespace"
  oc delete replicaset --all --namespace="$namespace"
  oc delete deployment.apps --all --namespace="$namespace"
  oc delete pod --all --namespace="$namespace"
  oc delete pvc --all --namespace="$namespace"
}

delete_kafka_resources kafka-example kafka-cluster person-topic
delete_deployment_and_services kafka-example kafka-ui kafka-ui kafka-ui
delete_deployment_and_services kafka-example schema-registry schema-registry schema-registry
delete_all_resources kafka-example

echo "Cleanup completed."

# LAST OPTION TO DELETE ALL
# oc get all -o name | awk 'system("oc delete " $1)'