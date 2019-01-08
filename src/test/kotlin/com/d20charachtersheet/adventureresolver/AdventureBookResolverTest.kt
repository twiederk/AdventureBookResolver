package com.d20charachtersheet.adventureresolver

import com.d20charachtersheet.adventureresolver.domain.BookEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AdventureBookResolverTest {

    @Test
    internal fun `start new adventure at vertex 1`() {
        // Arrange

        // Act
        val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")

        // Assert
        assertThat(adventureBookResolver.title).isEqualTo("Der Forst der Finsternis")
        assertThat(adventureBookResolver.dumpGraph()).isEqualTo("([BookEntry(id=1)], [])")
        assertThat(adventureBookResolver.currentEntry).isEqualTo(BookEntry(1))
    }

    @Test
    internal fun `add title to current book entry`() {
        // Arrange
        val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")

        // Act
        adventureBookResolver.setEntryTitle("Yaztronmos Behausung")

        // Assert
        assertThat(adventureBookResolver.getEntryTitle()).isEqualTo("Yaztronmos Behausung")
    }

    @Test
    internal fun `add new book entry`() {
        // Arrange
        val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")

        // Act
        adventureBookResolver.addBookEntry(261)

        // Assert
        assertThat(adventureBookResolver.dumpGraph()).isEqualTo("([BookEntry(id=1), BookEntry(id=261)], [(BookEntry(id=1),BookEntry(id=261))])")
    }

//    assertThat(graph.vertexSet()).hasSameElementsAs(setOf(1, 2, 3, 4, 5, 6))
//    assertThat(graph.toString()).isEqualTo("([1, 2, 3, 4, 5, 6], [(1,2), (1,3), (3,1), (1,4), (4,5), (4,6)])")

}