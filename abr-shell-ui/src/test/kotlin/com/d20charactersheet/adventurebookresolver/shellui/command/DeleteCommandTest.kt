package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class DeleteCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: DeleteCommand

    @Test
    fun `Delete actions`() {
        // Arrange
        adventureBookResolver.book = mock {
            on { getActions() }.doReturn(setOf(com.d20charactersheet.adventurebookresolver.core.domain.Action("downstairs", mock(), com.d20charactersheet.adventurebookresolver.core.domain.BookEntry(1))))
        }


        // Act
        underTest.delete(100)

        // Assert
        verify(adventureBookResolver.book).delete(100)
        verify(consoleService).write("downstairs -> 1")
    }

}