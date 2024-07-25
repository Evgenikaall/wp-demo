package com.wp.servicea.person.consumer;

import com.wp.model.Person;
import com.wp.servicea.person.mapper.PersonMapperA;
import com.wp.servicea.person.service.PersonProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TIMESTAMP;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonConsumer {

    private final PersonMapperA personMapper;

    private final PersonProcessor personProcessor;

    @KafkaListener(
            id = "person-topic-id",
            groupId = "person-group",
            topics = "${person-topic.name}"
    )
    private void listen(
            final Person person,
            @Header(value = OFFSET, required = false) final Long offset,
            @Header(value = RECEIVED_KEY, required = false) final String key,
            @Header(value = RECEIVED_TOPIC, required = false) final String topic,
            @Header(value = RECEIVED_PARTITION, required = false) final Integer partition,
            @Header(value = RECEIVED_TIMESTAMP, required = false) final long offsetDateTime) {
        log.info(
                "Receive data: \"{}\", from topic: {}, by key: \"{}\", from partition: {}, by offset: {},"
                        + " with time: {}",
                person,
                topic,
                key,
                partition,
                offset,
                Instant.ofEpochMilli(offsetDateTime));
        personProcessor.process(personMapper.map(person));
    }

  /*
     example of multi-structured topic
      @KafkaListener(id = "genericListener", topics = "bilingual-topic")
      static class MultiListenerBean {

          @KafkaHandler
          public void listen(Car car) {
              ...
          }

          @KafkaHandler
          public void listen(Bike bike) {
              ...
          }

          @KafkaHandler
          public void listen(Vehicle vehicle) {
                   ...
           }

          @KafkaHandler(isDefault = true)
          public void listenDefault(Object object) {
              ...
          }
      }
  */
}
