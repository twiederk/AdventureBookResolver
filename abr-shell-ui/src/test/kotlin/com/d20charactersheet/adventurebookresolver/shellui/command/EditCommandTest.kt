package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.shell.jline.InteractiveShellApplicationRunner
import org.springframework.shell.jline.ScriptShellApplicationRunner
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(properties = [
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"])
class EditCommandTest {

    @Autowired
    lateinit var underTest: EditCommand

    @Autowired
    lateinit var adventureBookResolver: AdventureBookResolver

    @AfterEach
    internal fun tearDown() {
        adventureBookResolver.book = AdventureBook()
    }

    @Test
    internal fun `edit title of entry`() {
        // Arrange

        // Act
        underTest.edit("Introduction")

        // Assert
        assertThat(adventureBookResolver.book.getEntryTitle()).isEqualTo("Introduction")
    }
}