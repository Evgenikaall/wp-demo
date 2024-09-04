package com.wp.app;

import com.wp.exceptions.KafkaRouteException;
import com.wp.processor.KafkaErrorMessageProcessor;
import com.wp.processor.KafkaMessageAProcessor;
import com.wp.processor.KafkaMessageBProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaCamelRoute extends RouteBuilder {

    private static final String KAFKA_ENDPOINT = "kafka:%s";

    @Value("${application.kafka.topic.raw-data-topic}")
    private String rawDataTopic;

    @Value("${application.kafka.topic.cooked-data-topic}")
    private String cookedDataTopic;

    @Override
    public void configure() {
        log.info("KafkaCamelRoute configure");

        //errorHandler(deadLetterChannel("seda:error"));

        onException(KafkaRouteException.class)
                .handled(true)
                .log("Error occurred: ${exception.message}")
                .process(new KafkaErrorMessageProcessor());

        from("direct:start")
                .log("Http endpoint called: ${body}")
                .toF(KAFKA_ENDPOINT, rawDataTopic);

        fromF(KAFKA_ENDPOINT, rawDataTopic)
                .log("Received message from Kafka: ${body}")
                .choice()
                .when(body().contains("identifier")).to("direct:processMessageA")
                .otherwise().to("direct:processMessageB");

        from("direct:processMessageA")
                .log("Processing message A: ${body}")
                .process(new KafkaMessageAProcessor())
                .toF(KAFKA_ENDPOINT, cookedDataTopic);

        from("direct:processMessageB")
                .log("Processing message B: ${body}")
                .process(new KafkaMessageBProcessor())
                .toF(KAFKA_ENDPOINT, cookedDataTopic);
    }
}
