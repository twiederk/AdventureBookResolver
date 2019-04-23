package com.d20charachtersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BookEntryTest {

    @Test
    fun `create book entry with id`() {
        // Act
        val bookEntry = BookEntry(1)

        // Assert
        assertThat(bookEntry.id).isEqualTo(1)
        assertThat(bookEntry.title).isEqualTo("Untitled")
        assertThat(bookEntry.visit).isEqualTo(Visit.UNVISITED)
        assertThat(bookEntry.note).isEmpty()
    }

    @Test
    fun `create book entry with id and title`() {
        // Act
        val bookEntry = BookEntry(1, "myTitle")

        // Assert
        assertThat(bookEntry.id).isEqualTo(1)
        assertThat(bookEntry.title).isEqualTo("myTitle")
        assertThat(bookEntry.visit).isEqualTo(Visit.UNVISITED)
        assertThat(bookEntry.note).isEmpty()
    }

    @Test
    fun `create book entry with id, title and visit`() {
        // Act
        val bookEntry = BookEntry(1, "myTitle", Visit.VISITED)

        // Assert
        assertThat(bookEntry.id).isEqualTo(1)
        assertThat(bookEntry.title).isEqualTo("myTitle")
        assertThat(bookEntry.visit).isEqualTo(Visit.VISITED)
        assertThat(bookEntry.note).isEmpty()
    }

    @Test
    fun `create book entry with id, title, visit and note`() {
        // Act
        val bookEntry = BookEntry(1, "myTitle", Visit.VISITED, "myNote")

        // Assert
        assertThat(bookEntry.id).isEqualTo(1)
        assertThat(bookEntry.title).isEqualTo("myTitle")
        assertThat(bookEntry.visit).isEqualTo(Visit.VISITED)
        assertThat(bookEntry.note).isEqualTo("myNote")
    }

    @Test
    fun `toString without note`() {
        // Arrange
        val bookEntry = BookEntry(1, "myTitle")

        // Act
        val toString = bookEntry.toString()

        // Assert
        assertThat(toString).isEqualTo("(1) - myTitle")
    }

    @Test
    fun `toString with note`() {
        // Arrange
        val bookEntry = BookEntry(1, "myTitle").apply { note = "myNote" }

        // Act
        val toString = bookEntry.toString()

        // Assert
        assertThat(toString).isEqualTo("""(1) - myTitle
            |myNote
        """.trimMargin())
    }
}