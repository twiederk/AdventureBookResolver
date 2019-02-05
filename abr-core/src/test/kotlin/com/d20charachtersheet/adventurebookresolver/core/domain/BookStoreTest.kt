package com.d20charachtersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class BookStoreTest {

    private val underTest = BookStore()

    @Nested
    inner class SaveTest {

        private val book = AdventureBook("book title")


        @BeforeEach
        internal fun setup() {
            with(book) {
                editBookEntry("Hallway")
                addAction("upstairs", 261)
                addAction("downstairs", 54)
                moveToBookEntry(261)
                editBookEntry("Library")
            }
        }

        @Test
        internal fun `export whole book`() {

            // Act
            val export = underTest.export(book)

            // Assert
            assertThat(export).isEqualToNormalizingNewlines("""TITLE=book title
                |CURRENT_BOOK_ENTRY=261
                |BOOK_ENTRY=1,Hallway,VISITED
                |BOOK_ENTRY=261,Library,VISITED
                |BOOK_ENTRY=54,Untitled,UNVISITED
                |LABELED_EDGE=1,261,upstairs
                |LABELED_EDGE=1,54,downstairs
                |ACTION=upstairs,1,261
                |
            """.trimMargin())
        }

        @Test
        internal fun `save book`() {
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
        internal fun `import whole book`() {
            // Arrange
            val importData: List<String> = listOf(
                    "TITLE=load title", //
                    "CURRENT_BOOK_ENTRY=261", //
                    "BOOK_ENTRY=1,Hallway,VISITED", //
                    "BOOK_ENTRY=261,Library,VISITED", //
                    "BOOK_ENTRY=54,Untitled,UNVISITED", //
                    "LABELED_EDGE=1,261,upstairs", //
                    "LABELED_EDGE=1,54,downstairs", //
                    "ACTION=upstairs,1,261")

            // Act
            val importedBook = underTest.import(importData)

            // Assert
            assertThat(importedBook.title).isEqualTo("load title")
            assertThat(importedBook.getAllBookEntries()).containsExactlyInAnyOrder(BookEntry(1), BookEntry(261), BookEntry(54))
            assertThat(importedBook.getActionsToUnvisitedEntries()).containsExactly(Action("downstairs", BookEntry(1), BookEntry(54)))
            assertThat(importedBook.getEntryId()).isEqualTo(261)
            assertThat(importedBook.getEntryTitle()).isEqualTo("Library")
            assertThat(importedBook.getPerformedActions()).containsExactly(Action("upstairs", BookEntry(1), BookEntry(261)))
        }

        @Test
        internal fun `load book`() {

            // Act
            val loadedBook = underTest.load("src/test/resources/loadBook.abr")

            // Assert
            assertThat(loadedBook.title).isEqualTo("load title")
            assertThat(loadedBook.getAllBookEntries()).containsExactlyInAnyOrder(BookEntry(1), BookEntry(261), BookEntry(54))
            assertThat(loadedBook.getActionsToUnvisitedEntries()).containsExactly(Action("downstairs", BookEntry(1), BookEntry(54)))
            assertThat(loadedBook.getEntryId()).isEqualTo(261)
            assertThat(loadedBook.getEntryTitle()).isEqualTo("Library")
            assertThat(loadedBook.getPerformedActions()).containsExactly(Action("upstairs", BookEntry(1), BookEntry(261)))
        }
    }

}
