package com.wp.servicea.order.consumer;

import com.wp.model.Order;
import com.wp.servicea.order.mapper.OrderModelConsumerMapper;
import com.wp.servicea.order.processor.OrderProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static org.springframework.kafka.support.KafkaHeaders.CORRELATION_ID;
import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TIMESTAMP;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderProcessor orderProcessor;

    private final OrderModelConsumerMapper mapper;

    @KafkaListener(
            id = "order-topic-id",
            groupId = "order-group",
            topics = "${order-topic.name}"
    )
    private void listen(
            final Order order,
            @Header(value = RECEIVED_TOPIC) final String topic,
            @Header(value = OFFSET, required = false) final Long offset,
            @Header(value = RECEIVED_KEY, required = false) final String key,
            @Header(value = RECEIVED_PARTITION, required = false) final Integer partition,
            @Header(value = CORRELATION_ID, required = false) final String correlationId,
            @Header(value = RECEIVED_TIMESTAMP, required = false) final long offsetDateTime) {

        // very bad example of logging
        log.info(
                "[{}] Receive data: \"{}\", from topic: {}, by key: \"{}\", from partition: {}, by offset: {}, with time: {}",
                correlationId,
                order,
                topic,
                key,
                partition,
                offset,
                Instant.ofEpochMilli(offsetDateTime)
        );

        orderProcessor.process(mapper.map(order));

    }
}
