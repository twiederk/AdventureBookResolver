package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.assertj.core.api.Assertions.assertThat
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
class MoveCommandTest {

    @Autowired
    lateinit var adventureBookResolver: AdventureBookResolver

    @Autowired
    lateinit var underTest: MoveCommand

    @Test
    internal fun `move to other book entry`() {
        // Arrange
        adventureBookResolver.book.addAction("nach oben", 261)

        // Act
        underTest.move(261)

        // Assert
        assertThat(adventureBookResolver.book.getEntryId()).isEqualTo(261)
    }
}