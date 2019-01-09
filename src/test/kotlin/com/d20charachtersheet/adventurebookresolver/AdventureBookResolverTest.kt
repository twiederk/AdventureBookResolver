package com.d20charachtersheet.adventurebookresolver

import com.d20charachtersheet.adventurebookresolver.domain.BookEdge
import com.d20charachtersheet.adventurebookresolver.domain.BookEntry
import com.d20charachtersheet.adventurebookresolver.domain.Visit
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
        assertThat(adventureBookResolver.currentBookEntry).isEqualTo(BookEntry(1))
        assertThat(adventureBookResolver.currentBookEntry.visit).isEqualTo(Visit.VISITED)
    }

    @Test
    internal fun `set title of current book entry (command edit)`() {
        // Arrange
        val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")

        // Act
        adventureBookResolver.setEntryTitle("Yaztronmos Behausung")

        // Assert
        assertThat(adventureBookResolver.getEntryTitle()).isEqualTo("Yaztronmos Behausung")
    }

    @Test
    internal fun `add new book entry (command add)`() {
        // Arrange
        val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")

        // Act
        adventureBookResolver.addBookEntry(261, "nach oben")

        // Assert
        assertThat(adventureBookResolver.dumpGraph()).isEqualTo("([BookEntry(id=1), BookEntry(id=261)], [BookEdge(label=nach oben)=(BookEntry(id=1),BookEntry(id=261))])")
        val edge: BookEdge = adventureBookResolver.graph.getEdge(BookEntry(1), BookEntry(261))
        assertThat(edge.label).isEqualTo("nach oben")
        val targetBookEntry = adventureBookResolver.graph.getEdgeTarget(edge)
        assertThat(targetBookEntry.visit).isEqualTo(Visit.UNVISITED)
    }

    @Test
    internal fun `set new current book entry (command move)`() {
        // Arrange
        val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")
        adventureBookResolver.addBookEntry(261, "nach oben")

        // Act
        adventureBookResolver.moveToBookEntry(261)

        // Assert
        assertThat(adventureBookResolver.currentBookEntry).isEqualTo(BookEntry(261))
        assertThat(adventureBookResolver.currentBookEntry.visit).isEqualTo(Visit.VISITED)
    }

}