package com.wp.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class KafkaCamelRoute extends RouteBuilder {

    private final static String KAFKA_ENDPOINT = "kafka:%s";

    @Override
    public void configure() {
        log.info("KafkaCamelRoute configure");

        fromF(KAFKA_ENDPOINT, "ec-pet")
                .log("Received message from Kafka: ${body}")
                .process(exchange -> {
                    var body = exchange.getIn().getBody(String.class);
                    exchange.getIn().setBody(body.concat(StringUtils.SPACE + UUID.randomUUID()));
                })
                .to("direct:processMessage");

        from("direct:processMessage")
                .log("Processing message: ${body}")
                .toF(KAFKA_ENDPOINT, "ec-pet-2");
    }
}
