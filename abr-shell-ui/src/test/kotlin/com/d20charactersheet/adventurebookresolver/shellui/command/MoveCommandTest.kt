package com.d20charactersheet.adventurebookresolver.shellui.command

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.inOrder
import org.springframework.beans.factory.annotation.Autowired

internal class MoveCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: MoveCommand

    @Test
    fun `move to other book entry`() {
        // Arrange
        adventureBookResolver.book.addAction("upstairs", 261)

        // Act
        underTest.move(261)

        // Assert
        assertThat(adventureBookResolver.book.getEntryId()).isEqualTo(261)
    }

    @Test
    fun `move to existing book entry and display available actions`() {
        // Arrange
        adventureBookResolver.book.apply {
            setEntryTitle("Hallway")
            addAction("upstairs", 100)
            moveToBookEntry(100)
            setEntryTitle("Library")
            setEntryNote("a lot of books")
            addAction("downstairs", 1)
            addAction("take book", 200)
            restart()
        }
        Mockito.reset(consoleService)

        // Act
        underTest.move(100)

        // Assert
        assertThat(adventureBookResolver.book.getEntryId()).isEqualTo(100)
        inOrder(consoleService) {
            verify(consoleService).write("(100) - Library: a lot of books")
            verify(consoleService).write("downstairs -> 1")
            verify(consoleService).write("take book -> 200")
        }

    }
}