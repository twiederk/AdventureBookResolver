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
            val underTest = AdventureBook("book title")

            // Assert
            assertThat(underTest.title).isEqualTo("book title")
            assertThat(underTest.getAllBookEntries()).isEqualTo(setOf(BookEntry(1)))
            assertThat(underTest.getEntryId()).isEqualTo(1)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

    }

    @Nested
    inner class CommandTest {

        private val underTest = AdventureBook("book title")

        @Test
        internal fun `edit title of current book entry (command edit)`() {
            // Act
            underTest.editBookEntry("entry title")

            // Assert
            assertThat(underTest.getEntryTitle()).isEqualTo("entry title")
        }

        @Test
        internal fun `add new action (command add)`() {
            // Act
            underTest.addAction("upstairs", 261)

            // Assert
            assertThat(underTest.getAllBookEntries()).extracting("id").hasSameElementsAs(setOf(1, 261))
            assertThat(underTest.getActions()).extracting("label").containsExactly("upstairs")
            assertThat(underTest.getNextBookEntries()).containsExactly(BookEntry(261)).extracting("visit").containsExactly(Visit.UNVISITED)
        }

        @Test
        internal fun `move to other book entry (command move)`() {
            // Arrange
            underTest.addAction("upstairs", 261)

            // Act
            underTest.moveToBookEntry(261)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(261)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

        @Test
        internal fun `move only possible to existing entry`() {
            // Arrange
            underTest.addAction("upstairs", 261)

            // Act
            underTest.moveToBookEntry(1000)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(1)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

        @Test
        internal fun `move only possible directly connected entry`() {
            // Arrange
            with(underTest) {
                addAction("upstairs", 261)
                moveToBookEntry(261)
            }

            // Act
            underTest.moveToBookEntry(1)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(261)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

        @Test
        internal fun `add already existing entry`() {
            // Arrange
            with(underTest) {
                editBookEntry("Introduction")
                addAction("upstairs", 261)
                moveToBookEntry(261)
                addAction("downstairs", 1)
            }

            // Act
            underTest.moveToBookEntry(1)

            // Assert
            assertThat(underTest.getEntryTitle()).isEqualTo("Introduction")
        }
    }

    @Nested
    inner class InformationTest {

        private val underTest = AdventureBook("book title")

        @BeforeEach
        internal fun setup() {
            with(underTest) {
                addAction("upstairs", 261)
                addAction("downstairs", 54)
            }
        }

        @Test
        internal fun `get list of actions of current book entry`() {
            // Act
            val actions: Set<Action> = underTest.getActions()

            // Assert
            assertThat(actions).containsExactlyInAnyOrder( //
                    Action("upstairs", BookEntry(1), BookEntry(261)), //
                    Action("downstairs", BookEntry(1), BookEntry(54)))
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

        @Test
        internal fun `get all performed actions`() {
            // Arrange
            with(underTest) {
                moveToBookEntry(261)
                addAction("downstairs", 1)
                moveToBookEntry(1)
            }

            // Act
            val performedActions = underTest.getPerformedActions()

            // Assert
            assertThat(performedActions).containsExactly( //
                    Action("upstairs", BookEntry(1), BookEntry(261)), //
                    Action("downstairs", BookEntry(261), BookEntry(1))) //
        }

        @Test
        internal fun `get list of visited entries`() {
            // Arrange
            with(underTest) {
                moveToBookEntry(261)
                addAction("downstairs", 1)
                moveToBookEntry(1)
            }

            // Act
            val visitedEntries = underTest.getPath()

            // Assert
            assertThat(visitedEntries).containsExactly(BookEntry(1), BookEntry(261), BookEntry(1))
        }

        @Test
        internal fun `get list of actions to unvisited entries`() {
            // Arrange

            // Act
            val openActions = underTest.getActionsToUnvisitedEntries()

            // Assert
            assertThat(openActions).containsExactlyInAnyOrder( //
                    Action("upstairs", BookEntry(1), BookEntry(261)), //
                    Action("downstairs", BookEntry(1), BookEntry(54)))
        }

    }

}