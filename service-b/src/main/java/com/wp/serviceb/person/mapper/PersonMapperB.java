package com.wp.serviceb.person.mapper;

import com.wp.model.Person;
import com.wp.serviceb.person.model.PersonModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PersonMapperB {

    Person map(final PersonModel personModel);
}
