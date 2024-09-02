package com.wp.app;

import com.wp.model.Person;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class KafkaCamelRoute extends RouteBuilder {

    private static final String KAFKA_ENDPOINT =
            "kafka:%s"
                    + "?additional-properties[auto.register.schemas]=false"
                    + "&additional-properties[avro.remove.java.properties]=true"
                    + "&additional-properties[value.subject.name.strategy]=io.confluent.kafka.serializers.subject.RecordNameStrategy"
//                    + "&additional-properties[use.schema.id]=1"
            ;

    @Value("${application.kafka.topic.raw-data-topic}")
    private String rawDataTopic;

    @Value("${application.kafka.topic.cooked-data-topic}")
    private String cookedDataTopic;

    @Override
    public void configure() {
        log.info("KafkaCamelRoute configure");

        from("direct:start")
                .log("Http endpoint called: ${body}")
                .process(exchange -> {
                    var person = exchange.getIn().getBody(Person.class);
                    exchange.getIn().setBody(person);
                })
                .toF(KAFKA_ENDPOINT, rawDataTopic);

        fromF(KAFKA_ENDPOINT, rawDataTopic)
                .log("Received message from Kafka: ${body}")
                .process(exchange -> {
                    Person person = exchange.getIn().getBody(Person.class);
                    person.setName(person.getName().concat(StringUtils.SPACE + UUID.randomUUID()));
                    exchange.getIn().setBody(person);
                })
                .to("direct:processMessage");

        from("direct:processMessage")
                .log("Processing message: ${body}")
                .toF(KAFKA_ENDPOINT, cookedDataTopic);
    }
}
