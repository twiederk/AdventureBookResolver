package com.d20charactersheet.adventurebookresolver.core.domain

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition
import org.junit.jupiter.api.Test

class ArchitectureTest {

    private val classes =
        ClassFileImporter().withImportOption(ImportOption.DoNotIncludeTests())
            .importPackages("com.d20charactersheet.adventurebookresolver.core")

    @Test
    fun noCyclicDependencies() {

        // Act
        val rule: ArchRule =
            SlicesRuleDefinition.slices().matching("com.d20charactersheet.adventurebookresolver.core.(**)")
                .should().beFreeOfCycles()

        // Assert
        rule.check(classes)
    }
}