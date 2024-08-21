package com.wp.servicea.person.processor;

import com.wp.servicea.person.model.PersonModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PersonProcessorImpl implements PersonProcessor {

    @Override
    public void process(final PersonModel personModel) {
        log.info("Service's processed: {}", personModel);
    }
}
