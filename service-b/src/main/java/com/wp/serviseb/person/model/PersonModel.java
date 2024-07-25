package com.wp.serviseb.person.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class PersonModel {

    private final Long identifier;

    private final String name;

    /**
     * Internal entity, not used in inter-service communication
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private boolean active = true;
}
