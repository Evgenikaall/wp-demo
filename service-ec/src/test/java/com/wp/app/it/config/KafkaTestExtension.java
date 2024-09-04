package com.wp.app.it.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.wp.app.it.config.KafkaContainerProvider.KAFKA_CONTAINER;

@Slf4j
public class KafkaTestExtension implements BeforeAllCallback, AfterAllCallback {

    private static final String CAMEL_COMPONENT_KAFKA_BROKER = "camel.component.kafka.brokers";

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        KAFKA_CONTAINER.start();

        log.info("Kafka container started");
        System.setProperty(CAMEL_COMPONENT_KAFKA_BROKER, KAFKA_CONTAINER.getBootstrapServers());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        KAFKA_CONTAINER.stop();
    }
}
