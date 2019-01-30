package com.d20charactersheet.adventurebookresolver.shellui.command

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class EditCommandTest : BaseCommandTest() {

    @Autowired
    lateinit var underTest: EditCommand

    @Test
    internal fun `edit title of entry`() {
        // Arrange

        // Act
        underTest.edit("Introduction")

        // Assert
        assertThat(adventureBookResolver.book.getEntryTitle()).isEqualTo("Introduction")
    }
}