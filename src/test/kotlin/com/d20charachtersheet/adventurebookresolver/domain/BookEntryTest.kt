package com.d20charachtersheet.adventurebookresolver.domain

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
        val bookEntry = BookEntry(1, "myTitle")

        // Assert
        assertThat(bookEntry.id).isEqualTo(1)
        assertThat(bookEntry.title).isEqualTo("myTitle")
    }
}