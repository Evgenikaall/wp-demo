package com.wp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:common.properties")
public class CamelKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamelKafkaApplication.class, args);
    }
}
