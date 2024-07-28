package com.wp.serviceb.person.service;

import com.wp.serviceb.person.model.PersonModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class PersonServiceImpl implements PersonService {
    @Override
    public Optional<PersonModel> getNextPerson() {
        return Optional.of(new PersonModel(new Random().nextLong(1000L), "Name"));
    }
}
