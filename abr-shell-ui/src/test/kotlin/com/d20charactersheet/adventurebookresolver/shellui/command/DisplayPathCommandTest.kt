package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.inOrder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class DisplayPathCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: DisplayPathCommand

    @Test
    internal fun `display path`() {

        // Arrange
        with(adventureBookResolver.book) {
            editBookEntry("Hallway")
            addAction("upstairs", 100)
            moveToBookEntry(100)
            editBookEntry("Tower")
            addAction("left", 200)
            moveToBookEntry(200)
            editBookEntry("Balcony")
        }

        // Act
        underTest.displayPath()

        // Assert
        inOrder(consoleService) {
            verify(consoleService).write("(1) - Hallway")
            verify(consoleService).write("(100) - Tower")
            verify(consoleService).write("(200) - Balcony")
        }
    }

}