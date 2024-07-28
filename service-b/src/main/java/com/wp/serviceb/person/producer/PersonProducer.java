package com.wp.serviceb.person.producer;

import com.wp.model.Person;
import com.wp.serviceb.person.mapper.PersonMapperB;
import com.wp.serviceb.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonProducer {

    private final KafkaTemplate<String, Person> kafkaTemplate;

    private final PersonService personService;

    private final PersonMapperB personMapper;

    @Value("${person-topic.name:person-topic}")
    private final String personTopic;

    @Scheduled(fixedDelay = 5L, timeUnit = TimeUnit.SECONDS)
    public void publishValidData() {
        final Person person =
                personService
                        .getNextPerson()
                        .map(personMapper::map)
                        .orElseThrow(() -> new IllegalArgumentException("Replace me"));

        kafkaTemplate.send(personTopic, person);
    }

}
