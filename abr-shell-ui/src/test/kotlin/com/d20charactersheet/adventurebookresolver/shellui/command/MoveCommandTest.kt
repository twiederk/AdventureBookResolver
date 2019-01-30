package com.d20charactersheet.adventurebookresolver.shellui.command

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class MoveCommandTest : BaseCommandTest() {

    @Autowired
    lateinit var underTest: MoveCommand

    @Test
    internal fun `move to other book entry`() {
        // Arrange
        adventureBookResolver.book.addAction("upstairs", 261)

        // Act
        underTest.move(261)

        // Assert
        assertThat(adventureBookResolver.book.getEntryId()).isEqualTo(261)
    }
}