package com.wp.serviceb.order.producer;

import com.wp.model.Order;
import com.wp.serviceb.order.mapper.OrderModelProducerMapper;
import com.wp.serviceb.order.service.OrderReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.kafka.support.KafkaHeaders.CORRELATION_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    private final OrderReservationService orderInitiationService;

    private final OrderModelProducerMapper mapper;

    @Value("${order-topic.name:order-topic}")
    private final String orderTopic;

    @Scheduled(fixedDelay = 5L, timeUnit = TimeUnit.SECONDS)
    public void publishValidData() {
        final Order order = orderInitiationService
                .prepareData(UUID.randomUUID())
                .map(mapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Ignore"));

        final ProducerRecord<String, Order> producerRecord = new ProducerRecord<>(orderTopic, order);

        final String correlationId = String.valueOf(UUID.randomUUID());

        producerRecord.headers().add(CORRELATION_ID, correlationId.getBytes(StandardCharsets.UTF_8));

        kafkaTemplate.send(producerRecord);

        // very bad example of logging
        log.info("[{}] Send message with data: {}, in topic: {}", correlationId, order, orderTopic);
    }
}
