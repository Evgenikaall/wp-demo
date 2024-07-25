package com.wp.servicea.person.mapper;

import com.wp.model.Person;
import com.wp.servicea.person.model.PersonModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonMapperATest {

    private final PersonMapperA mapper = new PersonMapperAImpl();

    @Test
    void shouldSuccessfullyMap(){
        // Given
        final Long identifier = 1L;
        final String name = "name";

        final Person dataToBeMapped = new Person(identifier, name);

        // When
        final PersonModel mappedData = mapper.map(dataToBeMapped);

        // Then
        assertAll(() -> {
            assertEquals(identifier, mappedData.identifier(), "Should be equals");
            assertEquals(name, mappedData.name(), "Should be equals");
        });
    }
  
}