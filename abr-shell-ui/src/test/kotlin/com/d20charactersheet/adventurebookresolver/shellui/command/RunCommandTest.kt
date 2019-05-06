package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.Action
import com.d20charachtersheet.adventurebookresolver.core.domain.BookEntry
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class RunCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: RunCommand

    @Test
    fun `Run to existing book entry`() {
        // Arrange
        adventureBookResolver.book = mock {
            on { getEntryId() }.doReturn(100)
            on { getEntryTitle() }.doReturn("Library")
            on { getActions() }.doReturn(setOf(Action("downstairs", mock(), BookEntry(1))))
        }

        // Act
        underTest.runTo(100)

        // Assert
        verify(adventureBookResolver.book).run(100)
        verify(consoleService).write("(100) - Library")
        verify(consoleService).write("downstairs -> 1")
    }

}