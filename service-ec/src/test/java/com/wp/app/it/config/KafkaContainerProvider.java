package com.wp.app.it.config;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public interface KafkaContainerProvider {

    DockerImageName KAFKA_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:7.6.1")
            .asCompatibleSubstituteFor("apache/kafka");

    KafkaContainer KAFKA_CONTAINER = new KafkaContainer(KAFKA_IMAGE);
}