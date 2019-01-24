package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.shell.jline.InteractiveShellApplicationRunner
import org.springframework.shell.jline.ScriptShellApplicationRunner
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(properties = [
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"])
class DisplayActionsToUnvisitedEntriesCommandTest {

    @MockBean
    lateinit var consoleService: ConsoleService

    @Autowired
    lateinit var underTest: DisplayActionsToUnvisitedEntriesCommand

    @Autowired
    lateinit var adventureBookResolver: AdventureBookResolver

    @AfterEach
    internal fun tearDown() {
        adventureBookResolver.book = AdventureBook()
    }

    @Test
    internal fun `display actions`() {
        // Arrange
        with(adventureBookResolver.book) {
            editBookEntry("Hallway")
            addAction("upstairs", 100)
            addAction("downstairs", 200)
            moveToBookEntry(100)
            editBookEntry("Tower")
            addAction("left", 300)
        }

        // Act
        underTest.displayActionsToUnvisitedEntries()

        // Assert
        verify(consoleService).write("(1) - Hallway: downstairs -> 200")
        verify(consoleService).write("(100) - Tower: left -> 300")
    }


}