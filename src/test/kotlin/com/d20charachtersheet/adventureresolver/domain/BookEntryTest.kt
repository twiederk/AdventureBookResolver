package com.d20charachtersheet.adventureresolver.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BookEntryTest {

    @Test
    internal fun `init book entry`() {
        // Act
        val bookEntry = BookEntry(1)

        // Assert
        assertThat(bookEntry.id).isEqualTo(1);
        assertThat(bookEntry.title).isEqualTo("Untitled");
    }

    @Test
    internal fun `dump book entry`() {
        // Act
        val result = BookEntry(1, "myTitle").toDump()

        // Assert
        assertThat(result).isEqualTo("BookEntry(id=1,title=myTitle)")
    }
}