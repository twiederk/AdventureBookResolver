package com.d20charachtersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AdventureBookTest {

    @Nested
    inner class StartupTest {

        @Test
        internal fun `start new adventure at vertex 1`() {
            // Act
            val underTest = AdventureBook("Der Forst der Finsternis")

            // Assert
            assertThat(underTest.title).isEqualTo("Der Forst der Finsternis")
            assertThat(underTest.getAllBookEntries()).isEqualTo(setOf(BookEntry(1)))
            assertThat(underTest.getEntryId()).isEqualTo(1)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

    }

    @Nested
    inner class CommandTest {

        private val underTest = AdventureBook("Der Forst der Finsternis")

        @Test
        internal fun `edit title of current book entry (command edit)`() {
            // Act
            underTest.editBookEntry("Yaztronmos Behausung")

            // Assert
            assertThat(underTest.getEntryTitle()).isEqualTo("Yaztronmos Behausung")
        }

        @Test
        internal fun `add new book entry (command add)`() {
            // Act
            underTest.addBookEntry(261, "nach oben")

            // Assert
            assertThat(underTest.getAllBookEntries()).extracting("id").hasSameElementsAs(setOf(1, 261))
            assertThat(underTest.getActions()).extracting("label").containsExactly("nach oben")
            assertThat(underTest.getNextBookEntries()).containsExactly(BookEntry(261)).extracting("visit").containsExactly(Visit.UNVISITED)
        }

        @Test
        internal fun `move to other book entry (command move)`() {
            // Arrange
            underTest.addBookEntry(261, "nach oben")

            // Act
            underTest.moveToBookEntry(261)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(261)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

        @Test
        internal fun `move only possible to existing entry`() {
            // Arrange
            underTest.addBookEntry(261, "nach oben")

            // Act
            underTest.moveToBookEntry(1000)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(1)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

        @Test
        internal fun `move only possible directly connected entry`() {
            // Arrange
            underTest.addBookEntry(261, "nach oben")
            underTest.moveToBookEntry(261)

            // Act
            underTest.moveToBookEntry(1)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(261)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }
    }

    @Nested
    inner class InformationTest {

        private val underTest = AdventureBook("Der Forst der Finsternis")

        @BeforeEach
        internal fun setup() {
            underTest.addBookEntry(261, "nach oben")
            underTest.addBookEntry(54, "Schwert ziehen")
        }

        @Test
        internal fun `get list of existing edges of current book entry`() {
            // Act
            val edges: Set<LabeledEdge> = underTest.getActions()

            // Assert
            assertThat(edges).extracting("label").containsExactlyInAnyOrder("nach oben", "Schwert ziehen")
        }

        @Test
        internal fun `get list of book entries to move to`() {
            // Act
            val bookEntries: Set<BookEntry> = underTest.getNextBookEntries()

            // Assert
            assertThat(bookEntries).extracting("title").containsExactly("Untitled", "Untitled")
        }

        @Test
        internal fun `get all vertices of graph`() {
            // Act
            val allBookEntries = underTest.getAllBookEntries()

            // Assert
            assertThat(allBookEntries).extracting("id").hasSameElementsAs(setOf(1, 261, 54))
        }

    }


}