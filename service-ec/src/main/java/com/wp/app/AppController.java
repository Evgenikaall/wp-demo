package com.wp.app;

import com.wp.model.Person;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    private final ProducerTemplate producerTemplate;

    public AppController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @PostMapping(value = "/produce-message")
    public ResponseEntity<String> produce(@RequestParam Long identifier, @RequestParam String name) {

        var person = Person.newBuilder()
                .setIdentifier(identifier)
                .setName(name)
                .build();

        producerTemplate.sendBody("direct:start", person);

        return ResponseEntity
                .status(201)
                .body("Message sent");
    }
}
