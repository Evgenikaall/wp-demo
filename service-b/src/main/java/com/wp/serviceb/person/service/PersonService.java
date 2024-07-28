package com.wp.serviceb.person.service;

import com.wp.serviceb.person.model.PersonModel;

import java.util.Optional;

/**
 * Represents {@link PersonModel} related operations
 */
public interface PersonService {

    /**
     * Provides person enquiring functionality (random)
     *
     * @return person
     */
    Optional<PersonModel> getNextPerson();
}
