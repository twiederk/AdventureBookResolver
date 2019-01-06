package com.d20charachtersheet.adventureresolver

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AdventureBookResolverTest {

    @Test
    internal fun `start with empty graph`() {
        // Arrange

        // Act
        val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")

        // Assert
        assertThat(adventureBookResolver.title).isEqualTo("Der Forst der Finsternis")
        assertThat(adventureBookResolver.dumpGraph()).isEqualTo("([], [])")
    }
}