package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.shell.BookPromptProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class CreateCommandTest : BaseCommandTest() {

    @Autowired
    lateinit var promptProvider: BookPromptProvider

    @Autowired
    lateinit var underTest: CreateCommand

    @Test
    internal fun `create new book`() {
        // Act
        underTest.create("my new book")

        // Assert
        assertThat(promptProvider.prompt.toString()).isEqualTo("(1) - Untitled> ")

    }
}