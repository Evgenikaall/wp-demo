package com.wp.servicea.person.service;

import com.wp.servicea.person.model.PersonModel;

/**
 * Represents {@link PersonModel} related operations
 */
public interface PersonProcessor {

    /**
     * Something custom @param personModel data for processing
     */
    void process(final PersonModel personModel);
}
