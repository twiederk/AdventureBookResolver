package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.BookEntry
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


class AddCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: AddCommand

    @Test
    internal fun `add new entry to book`() {
        // Act
        underTest.add("upstairs", 261)

        // Assert
        assertThat(adventureBookResolver.book.getNextBookEntries()).containsExactly(BookEntry(261))
        verify(consoleService).write("upstairs -> 261")
    }

}