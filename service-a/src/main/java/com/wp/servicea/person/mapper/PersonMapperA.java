package com.wp.servicea.person.mapper;

import com.wp.model.Person;
import com.wp.servicea.person.model.PersonModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PersonMapperA {

    PersonModel map(final Person personModel);
}
