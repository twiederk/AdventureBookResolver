package com.d20charachtersheet.adventurebookresolver

import com.d20charachtersheet.adventurebookresolver.domain.BookEdge
import com.d20charachtersheet.adventurebookresolver.domain.BookEntry
import com.d20charachtersheet.adventurebookresolver.domain.Visit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AdventureBookResolverTest {

    @Nested
    inner class StartupTest {
        @Test
        internal fun `start new adventure at vertex 1`() {
            // Arrange

            // Act
            val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")

            // Assert
            assertThat(adventureBookResolver.title).isEqualTo("Der Forst der Finsternis")
            assertThat(adventureBookResolver.dumpGraph()).isEqualTo("([BookEntry(id=1)], [])")
            assertThat(adventureBookResolver.getEntryId()).isEqualTo(1)
            assertThat(adventureBookResolver.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

    }

    @Nested
    inner class CommandTest {
        @Test
        internal fun `edit title of current book entry (command edit)`() {
            // Arrange
            val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")

            // Act
            adventureBookResolver.editBookEntry("Yaztronmos Behausung")

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
            assertThat(adventureBookResolver.dumpGraph()).isEqualTo("([BookEntry(id=1), BookEntry(id=261)], [(BookEntry(id=1) : BookEntry(id=261))=(BookEntry(id=1),BookEntry(id=261))])")
            val edge: BookEdge = adventureBookResolver.graph.getEdge(BookEntry(1), BookEntry(261))
            assertThat(edge.label).isEqualTo("nach oben")
            val targetBookEntry = adventureBookResolver.graph.getEdgeTarget(edge)
            assertThat(targetBookEntry.visit).isEqualTo(Visit.UNVISITED)
        }

        @Test
        internal fun `move to other book entry (command move)`() {
            // Arrange
            val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")
            adventureBookResolver.addBookEntry(261, "nach oben")

            // Act
            adventureBookResolver.moveToBookEntry(261)

            // Assert
            assertThat(adventureBookResolver.getEntryId()).isEqualTo(261)
            assertThat(adventureBookResolver.getEntryVisit()).isEqualTo(Visit.VISITED)
        }
    }

    @Nested
    inner class InformationTest {

        @Test
        internal fun `get list of existing edges of current book entry`() {
            // Arrange
            val adventureBookResolver = AdventureBookResolver("Der Forst der Finsternis")
            adventureBookResolver.addBookEntry(261, "nach oben")
            adventureBookResolver.addBookEntry(54, "Schwert ziehen")

            // Act
            val edges: Set<BookEdge> = adventureBookResolver.getEdges()

            // Assert
            assertThat(edges).extracting("label").containsExactlyInAnyOrder("nach oben", "Schwert ziehen")
        }
    }


}