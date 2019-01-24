package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charachtersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
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
class AddCommandTest {

    @MockBean
    lateinit var consoleService: ConsoleService

    @Autowired
    lateinit var adventureBookResolver: AdventureBookResolver

    @Autowired
    lateinit var underTest: AddCommand

    @AfterEach
    internal fun tearDown() {
        adventureBookResolver.book = AdventureBook()
    }

    @Test
    internal fun `add new entry to book`() {
        // Act
        underTest.add("upstairs", 261)

        // Assert
        assertThat(adventureBookResolver.book.getNextBookEntries()).containsExactly(BookEntry(261))
        verify(consoleService).write("upstairs -> 261")
    }

}