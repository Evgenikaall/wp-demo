package com.wp.processor;

import com.wp.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class KafkaErrorMessageProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaErrorMessageProcessor.class);
    public void process(Exchange exc) {
        LOG.info("Handling exceptions");
    }

}
