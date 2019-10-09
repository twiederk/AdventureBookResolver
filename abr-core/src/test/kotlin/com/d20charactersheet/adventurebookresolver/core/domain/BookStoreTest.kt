package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class BookStoreTest {

    private val underTest = BookStore()

    @Nested
    inner class SaveTest {

        private val book = AdventureBook(title = "book title", attributes = Attributes(
                dexterity = Attribute(AttributeName.DEXTERITY, 9, 10),
                strength = Attribute(AttributeName.STRENGTH, 12, 24),
                luck = Attribute(AttributeName.LUCK, 5, 8)
        ))

        @BeforeEach
        fun setup() {
            book.apply {
                addItemToInventory("sword")
                addItemToInventory("leather armor")
                addItemToInventory("backpack")
                editBookEntry("Hallway")
                note("Start of adventure")
                addAction("upstairs", 261)
                addAction("downstairs", 54)
                moveToBookEntry(261)
                editBookEntry("Library")
            }
        }

        @Test
        fun `export whole book`() {

            // Act
            val export = underTest.export(book)

            // Assert
            assertThat(export).isEqualToNormalizingNewlines("""TITLE=book title
                |TRIES=1
                |CURRENT_BOOK_ENTRY=261
                |DEXTERITY=9,10
                |STRENGTH=12,24
                |LUCK=5,8
                |ITEM=sword
                |ITEM=leather armor
                |ITEM=backpack
                |BOOK_ENTRY=1,Hallway,VISITED,Start of adventure
                |BOOK_ENTRY=261,Library,VISITED,
                |BOOK_ENTRY=54,Untitled,UNVISITED,
                |LABELED_EDGE=1,261,upstairs
                |LABELED_EDGE=1,54,downstairs
                |ACTION=upstairs,1,261
                |
            """.trimMargin())
        }

        @Test
        fun `save book`() {
            // Act
            val savedBook = underTest.save(book, "myBook")

            // Assert
            assertThat(savedBook.toString()).endsWith("myBook.abr")
            assertThat(savedBook).hasSameContentAs(Paths.get("src/test/resources/expected_savedBook.abr").toAbsolutePath())

            // tear down
            Files.delete(savedBook)
        }

    }

    @Nested
    inner class LoadTest {


        @Test
        fun `import whole book`() {
            // Arrange
            val importData: List<String> = listOf(
                    "TITLE=load title", //
                    "TRIES=2", //
                    "CURRENT_BOOK_ENTRY=261", //
                    "DEXTERITY=9,10", //
                    "STRENGTH=12,24", //
                    "LUCK=5,8", //
                    "ITEM=sword", //
                    "ITEM=leather armor", //
                    "ITEM=backpack", //
                    "BOOK_ENTRY=1,Hallway,VISITED,Start of adventure", //
                    "BOOK_ENTRY=261,Library,VISITED,", //
                    "BOOK_ENTRY=54,Untitled,UNVISITED,", //
                    "LABELED_EDGE=1,261,upstairs", //
                    "LABELED_EDGE=1,54,downstairs", //
                    "ACTION=upstairs,1,261")

            // Act
            val importedBook = underTest.import(importData)

            // Assert
            assertThat(importedBook.title).isEqualTo("load title")
            assertThat(importedBook.tries).isEqualTo(2)
            AttributeAssert.assertThat(importedBook.attributes.dexterity).name(AttributeName.DEXTERITY).value(9).maxValue(10)
            AttributeAssert.assertThat(importedBook.attributes.strength).name(AttributeName.STRENGTH).value(12).maxValue(24)
            AttributeAssert.assertThat(importedBook.attributes.luck).name(AttributeName.LUCK).value(5).maxValue(8)
            assertThat(importedBook.getItems()).extracting("name").containsExactly("sword", "leather armor", "backpack")
            assertThat(importedBook.getAllBookEntries()).containsExactlyInAnyOrder(BookEntry(1), BookEntry(261), BookEntry(54))
            assertThat(importedBook.getActionsToUnvisitedEntries()).containsExactly(Action("downstairs", BookEntry(1), BookEntry(54)))
            assertThat(importedBook.getEntryId()).isEqualTo(261)
            assertThat(importedBook.getEntryTitle()).isEqualTo("Library")
            assertThat(importedBook.getPerformedActions()).containsExactly(Action("upstairs", BookEntry(1), BookEntry(261)))
            assertThat(importedBook.getPerformedActions()[0].source.note).isEqualTo("Start of adventure")
        }

        @Test
        fun `load book`() {

            // Act
            val loadedBook = underTest.load("src/test/resources/loadBook")

            // Assert
            assertThat(loadedBook.title).isEqualTo("load title")
            assertThat(loadedBook.tries).isEqualTo(2)
            AttributeAssert.assertThat(loadedBook.attributes.dexterity).name(AttributeName.DEXTERITY).value(9).maxValue(10)
            AttributeAssert.assertThat(loadedBook.attributes.strength).name(AttributeName.STRENGTH).value(12).maxValue(24)
            AttributeAssert.assertThat(loadedBook.attributes.luck).name(AttributeName.LUCK).value(5).maxValue(8)
            assertThat(loadedBook.getItems()).extracting("name").containsExactly("sword", "leather armor", "backpack")
            assertThat(loadedBook.getAllBookEntries()).containsExactlyInAnyOrder(BookEntry(1), BookEntry(261), BookEntry(54))
            assertThat(loadedBook.getActionsToUnvisitedEntries()).containsExactly(Action("downstairs", BookEntry(1), BookEntry(54)))
            assertThat(loadedBook.getEntryId()).isEqualTo(261)
            assertThat(loadedBook.getEntryTitle()).isEqualTo("Library")
            assertThat(loadedBook.getPerformedActions()).containsExactly(Action("upstairs", BookEntry(1), BookEntry(261)))
        }

    }

}
