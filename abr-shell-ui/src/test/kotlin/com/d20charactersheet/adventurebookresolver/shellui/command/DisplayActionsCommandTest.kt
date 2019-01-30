package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class DisplayActionsCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: DisplayActionsCommand

    @Test
    internal fun `display actions`() {
        // Arrange
        adventureBookResolver.book.addAction("upstairs", 261)

        // Act
        underTest.displayActions()

        // Assert
        verify(consoleService).write("upstairs -> 261")
    }
}

