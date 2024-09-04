package com.wp.processor;

import com.wp.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class KafkaMessageAProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageAProcessor.class);
    public void process(Exchange exc) {
        Person person = exc.getIn().getBody(Person.class);
        person.setName(person.getName().concat(StringUtils.SPACE + UUID.randomUUID()));
        exc.getIn().setBody(person);
    }

}
