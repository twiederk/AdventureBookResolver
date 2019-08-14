package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.Die
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

internal class RollDieCommandTest : BaseConsoleCommandTest() {

    @MockBean
    lateinit var die: Die

    @Autowired
    lateinit var underTest: RollDieCommand

    @Test
    fun `roll default die`() {

        // Arrange
        whenever(die.roll(any())).thenReturn(4)

        // Act
        underTest.roll()

        // Assert
        verify(consoleService).write("You rolled 1d6 and scored: 4")
    }

}