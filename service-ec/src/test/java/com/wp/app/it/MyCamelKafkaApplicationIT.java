package com.wp.app.it;

import com.wp.app.it.config.KafkaTestExtension;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(KafkaTestExtension.class)
class MyCamelKafkaApplicationIT {

    public static String KAFKA_ENDPOINT = "kafka:%s?";

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Test
    public void testRoutes() {
        // Send a message to the first topic
        producerTemplate.sendBody(KAFKA_ENDPOINT.formatted("ec-pet"), "Hello Kafka");

        // Verify that the message was consumed, processed, and sent to the second topic
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            String consumedMessage = consumerTemplate.receiveBody(KAFKA_ENDPOINT.formatted("ec-pet-2"), String.class);
            log.info("Consumed test message: {}", consumedMessage);
            Assertions.assertTrue(consumedMessage.startsWith("Hello Kafka"));
        });
    }
}