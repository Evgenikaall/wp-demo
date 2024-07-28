package com.wp.serviceb.person.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class DependencyRulesTest {

    @Test
    public void producerPackageShouldNotDependOnModelTestPackage() {
        // Given
        final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.wp.serviceb");

        // When
        noClasses()
                .that().resideOutsideOfPackages("..producer..", "..mapper..")
                .should().dependOnClassesThat().resideInAnyPackage("com.wp.model")
        // Then
                .check(importedClasses);
    }
}