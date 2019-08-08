package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.Die
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class RollDieCommandTest : BaseConsoleCommandTest() {


    @Test
    fun `display inventory`() {

        // Arrange
        val underTest = RollDieCommand(Die(Random(1)), consoleService)

        // Act
        underTest.roll()

        // Assert
        verify(consoleService).write("You rolled a 4")
    }


}