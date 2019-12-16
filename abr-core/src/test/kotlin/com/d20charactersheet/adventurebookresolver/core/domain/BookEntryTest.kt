package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BookEntryTest {

    @Test
    fun `create book entry with id`() {
        // Act
        val bookEntry = BookEntry(1)

        // Assert
        assertThat(bookEntry.id).isEqualTo(1)
        assertThat(bookEntry.title).isEqualTo(BOOK_ENTRY_DEFAULT_TITLE)
        assertThat(bookEntry.visit).isEqualTo(Visit.UNVISITED)
        assertThat(bookEntry.wayMark).isEqualTo(WayMark.NORMAL)
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
        assertThat(bookEntry.wayMark).isEqualTo(WayMark.NORMAL)
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
        assertThat(bookEntry.wayMark).isEqualTo(WayMark.NORMAL)
        assertThat(bookEntry.note).isEmpty()
    }

    @Test
    fun `create book entry with id, title and way mark`() {
        // Act
        val bookEntry = BookEntry(1, "myTitle", Visit.VISITED, WayMark.WAY_POINT)

        // Assert
        assertThat(bookEntry.id).isEqualTo(1)
        assertThat(bookEntry.title).isEqualTo("myTitle")
        assertThat(bookEntry.visit).isEqualTo(Visit.VISITED)
        assertThat(bookEntry.wayMark).isEqualTo(WayMark.WAY_POINT)
        assertThat(bookEntry.note).isEmpty()
    }

    @Test
    fun `create book entry with id, title, visit, way mark and note`() {
        // Act
        val bookEntry = BookEntry(1, "myTitle", Visit.VISITED, WayMark.WAY_POINT, "myNote")

        // Assert
        assertThat(bookEntry.id).isEqualTo(1)
        assertThat(bookEntry.title).isEqualTo("myTitle")
        assertThat(bookEntry.visit).isEqualTo(Visit.VISITED)
        assertThat(bookEntry.wayMark).isEqualTo(WayMark.WAY_POINT)
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