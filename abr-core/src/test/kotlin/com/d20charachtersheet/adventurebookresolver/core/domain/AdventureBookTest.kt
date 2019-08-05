package com.d20charachtersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class AdventureBookTest {

    @Nested
    inner class StartupTest {

        @Test
        fun `start new adventure at vertex 1`() {
            // Act
            val underTest = AdventureBook("book title")

            // Assert
            assertThat(underTest.title).isEqualTo("book title")
            assertThat(underTest.getAllBookEntries()).isEqualTo(setOf(BookEntry(1)))
            assertThat(underTest.getEntryId()).isEqualTo(1)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
            assertThat(underTest.tries).isEqualTo(1)
            assertThat(underTest.totalNumberOfBookEntries).isEqualTo(400)
            assertThat(underTest.getItems()).isEmpty()

            val attributes = underTest.attributes
            AttributeAssert.assertThat(attributes.dexterity).name(AttributeName.DEXTERITY).isBetween(7, 12)
            AttributeAssert.assertThat(attributes.strength).name(AttributeName.STRENGTH).isBetween(14, 24)
            AttributeAssert.assertThat(attributes.luck).name(AttributeName.LUCK).isBetween(7, 12)

        }

    }

    @Nested
    inner class CommandTest {

        private val underTest = AdventureBook("book title")

        @Test
        fun `edit title of current book entry (command edit)`() {
            // Act
            underTest.editBookEntry("entry title")

            // Assert
            assertThat(underTest.getEntryTitle()).isEqualTo("entry title")
        }

        @Test
        fun `add new action (command add)`() {
            // Act
            underTest.addAction("upstairs", 261)

            // Assert
            assertThat(underTest.getAllBookEntries()).extracting("id").hasSameElementsAs(setOf(1, 261))
            assertThat(underTest.getActions()).extracting("label").containsExactly("upstairs")
            assertThat(underTest.getNextBookEntries()).containsExactly(BookEntry(261)).extracting("visit").containsExactly(Visit.UNVISITED)
        }

        @Test
        fun `move to other book entry (command move)`() {
            // Arrange
            underTest.addAction("upstairs", 261)

            // Act
            underTest.moveToBookEntry(261)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(261)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

        @Test
        fun `move only possible to existing entry`() {
            // Arrange
            underTest.addAction("upstairs", 261)

            // Act
            underTest.moveToBookEntry(1000)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(1)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

        @Test
        fun `move only possible directly connected entry`() {
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
        fun `add already existing entry`() {
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

        @Test
        fun `add note to current entry`() {
            // Act
            underTest.note("myNote")

            // Assert
            assertThat(underTest.getEntryNote()).isEqualTo("myNote")
        }

        @Test
        fun `restart book`() {
            // Arrange
            underTest.apply {
                editBookEntry("Introduction")
                addAction("upstairs", 100)
                addAction("downstairs", 110)
                moveToBookEntry(100)
                editBookEntry("Library")
                addAction("Take a book", 200)
                addItemToInventory("Book")
            }
            val attributes = underTest.attributes

            // Act
            underTest.restart()

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(1)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
            assertThat(underTest.getEntryTitle()).isEqualTo("Introduction")
            assertThat(underTest.getAllBookEntries()).hasSize(4)
            assertThat(underTest.getAllBookEntries().map { it.visit }).containsExactlyInAnyOrder(Visit.VISITED, Visit.VISITED, Visit.UNVISITED, Visit.UNVISITED)
            assertThat(underTest.getPerformedActions()).isEmpty()
            assertThat(underTest.tries).isEqualTo(2)
            assertThat(underTest.getItems()).isEmpty()
            assertThat(underTest.attributes).isNotSameAs(attributes)
        }

        @Test
        fun `run to a book entry`() {
            // Arrange
            underTest.apply {
                editBookEntry("Introduction")
                addAction("upstairs", 100)
                addAction("downstairs", 200)
                moveToBookEntry(100)
                editBookEntry("Library")
                addAction("take book", 300)
                addAction("downstairs", 1)
                moveToBookEntry(300)
                editBookEntry("Select book to take")
                addAction("take red book", 301)
                addAction("take blue book", 302)
                moveToBookEntry(301)
                editBookEntry("Poisoned book")
                restart()
            }

            // Act
            underTest.run(300)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(300)
            assertThat(underTest.getEntryTitle()).isEqualTo("Select book to take")
            assertThat(underTest.getPath()).containsExactly(BookEntry(1), BookEntry(100), BookEntry(300))
            assertThat(underTest.getPerformedActions()).containsExactly(
                    Action("upstairs", BookEntry(1), BookEntry(100)), //
                    Action("take book", BookEntry(100), BookEntry(300))) //
        }

        @Test
        fun `search notes`() {
            // Arrange
            underTest.apply {
                note("Start of adventure")
            }

            // Act
            val bookEntries = underTest.search("start")

            // Assert
            assertThat(bookEntries).containsExactly(BookEntry(1))
        }

        @Test
        fun `search notes with no match`() {

            // Act
            val bookEntries = underTest.search("start")

            // Assert
            assertThat(bookEntries).isEmpty()
        }

        @Test
        fun `delete action with entry`() {
            // Arrange
            underTest.apply {
                addAction("upstairs", 100)
            }

            // Act
            underTest.delete(100)

            // Assert
            assertThat(underTest.getActions()).isEmpty()
            assertThat(underTest.getAllBookEntries()).containsExactly(BookEntry(1))
        }

        @Test
        fun `delete action but not entry`() {
            // Arrange
            underTest.apply {
                addAction("upstairs", 100)
                moveToBookEntry(100)
                addAction("take book", 200)
                moveToBookEntry(200)
                addAction("back", 100)
            }

            // Act
            underTest.delete(100)

            // Assert
            assertThat(underTest.getActions()).isEmpty()
            assertThat(underTest.getAllBookEntries()).containsExactly(BookEntry(1), BookEntry(100), BookEntry(200))
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
        fun `get list of actions of current book entry`() {
            // Act
            val actions: Set<Action> = underTest.getActions()

            // Assert
            assertThat(actions).containsExactlyInAnyOrder( //
                    Action("upstairs", BookEntry(1), BookEntry(261)), //
                    Action("downstairs", BookEntry(1), BookEntry(54)))
        }

        @Test
        fun `get list of book entries to move to`() {
            // Act
            val bookEntries: Set<BookEntry> = underTest.getNextBookEntries()

            // Assert
            assertThat(bookEntries).extracting("title").containsExactly(BOOK_ENTRY_DEFAULT_TITLE, BOOK_ENTRY_DEFAULT_TITLE)
        }

        @Test
        fun `get all vertices of graph`() {
            // Act
            val allBookEntries = underTest.getAllBookEntries()

            // Assert
            assertThat(allBookEntries).extracting("id").hasSameElementsAs(setOf(1, 261, 54))
        }

        @Test
        fun `get all performed actions`() {
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
        fun `get list of visited entries`() {
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
        fun `get list of actions to unvisited entries`() {
            // Arrange

            // Act
            val openActions = underTest.getActionsToUnvisitedEntries()

            // Assert
            assertThat(openActions).containsExactlyInAnyOrder( //
                    Action("upstairs", BookEntry(1), BookEntry(261)), //
                    Action("downstairs", BookEntry(1), BookEntry(54)))
        }

    }

    @Nested
    inner class InventoryTest {

        private val underTest = AdventureBook("book title")

        @Test
        fun `get items of inventory`() {
            // Act
            val items = underTest.getItems()

            // Assert
            assertThat(items).isEmpty()
        }

        @Test
        fun `add item to inventory`() {
            // Act
            underTest.addItemToInventory("ItemName")

            // Assert
            assertThat(underTest.getItems()).hasSize(1)
            assertThat(underTest.getItems()[0].name).isEqualTo("ItemName")
        }

        @Test
        fun `remove item from inventory`() {
            // Arrange
            underTest.addItemToInventory("myItem")

            // Act
            underTest.removeItemFromInventory(0)

            // Assert
            assertThat(underTest.getItems()).isEmpty()
        }

        @Test
        fun `remove item from empty inventory`() {
            // Act
            underTest.removeItemFromInventory(0)

            // Assert
            assertThat(underTest.getItems()).isEmpty()
        }

        @Test
        fun `remove item with negative index`() {
            // Act
            underTest.removeItemFromInventory(-1)

            // Assert
            assertThat(underTest.getItems()).isEmpty()
        }

        @Test
        fun `remove item in middle of list`() {
            // Arrange
            underTest.addItemToInventory("leather armor")
            underTest.addItemToInventory("sword")
            underTest.addItemToInventory("backpack")

            // Act
            underTest.removeItemFromInventory(1)

            // Assert
            assertThat(underTest.getItems()).containsExactly(Item("leather armor"), Item("backpack"))
        }

    }

}