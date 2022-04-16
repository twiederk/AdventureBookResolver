package com.d20charactersheet.adventurebookresolver.shellui.command

import org.junit.jupiter.api.Test
import org.mockito.kotlin.inOrder
import org.springframework.beans.factory.annotation.Autowired

internal class DisplayPathCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: DisplayPathCommand

    @Test
    fun `display path`() {

        // Arrange
        with(adventureBookResolver.book) {
            setEntryTitle("Hallway")
            addAction("upstairs", 100)
            setEntryNote("Start of adventure")
            moveToBookEntry(100)
            setEntryTitle("Tower")
            addAction("left", 200)
            moveToBookEntry(200)
            setEntryTitle("Balcony")
        }

        // Act
        underTest.displayPath()

        // Assert
        inOrder(consoleService) {
            verify(consoleService).write("Number of tries: 1")
            verify(consoleService).write("Known book entries: 3 / 400 (0.75%)")
            verify(consoleService).write("(1) - Hallway: Start of adventure")
            verify(consoleService).write("(100) - Tower")
            verify(consoleService).write("(200) - Balcony")
        }
    }

}