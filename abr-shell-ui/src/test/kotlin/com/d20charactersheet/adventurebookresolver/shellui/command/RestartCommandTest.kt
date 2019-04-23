package com.d20charactersheet.adventurebookresolver.shellui.command

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class RestartCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: RestartCommand

    @Test
    fun `Restart book at entry 1`() {
        // Act
        underTest.restart()

        // Assert
        with(adventureBookResolver.book) {
            assertThat(getEntryId()).isEqualTo(1)
            assertThat(getPerformedActions()).isEmpty()
            assertThat(tries).isEqualTo(2)
        }
    }
}