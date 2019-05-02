package com.d20charactersheet.adventurebookresolver.shellui

import com.d20charachtersheet.adventurebookresolver.core.domain.ADVENTURE_BOOK_DEFAULT_TITLE
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
class AdventureBookResolverApplicationTest {

    @Autowired
    lateinit var adventureBookResolver: AdventureBookResolver

    @Test
    internal fun `start with book called new book`() {
        // Assert
        assertThat(adventureBookResolver.book.title).isEqualTo(ADVENTURE_BOOK_DEFAULT_TITLE)
    }
}

