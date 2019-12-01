package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class DisplayActionsToUnvisitedEntriesCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: DisplayActionsToUnvisitedEntriesCommand

    @Test
    internal fun `display actions`() {
        // Arrange
        with(adventureBookResolver.book) {
            setEntryTitle("Hallway")
            addAction("upstairs", 100)
            addAction("downstairs", 200)
            moveToBookEntry(100)
            setEntryTitle("Tower")
            addAction("left", 300)
        }

        // Act
        underTest.displayActionsToUnvisitedEntries()

        // Assert
        verify(consoleService).write("(1) - Hallway: downstairs -> 200")
        verify(consoleService).write("(100) - Tower: left -> 300")
    }


}