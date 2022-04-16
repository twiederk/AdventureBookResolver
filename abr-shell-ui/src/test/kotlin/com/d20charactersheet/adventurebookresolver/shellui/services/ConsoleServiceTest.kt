package com.d20charactersheet.adventurebookresolver.shellui.services

import org.junit.jupiter.api.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import java.io.PrintStream

class ConsoleServiceTest {

    private val out = mock<PrintStream>()
    private val underTest = ConsoleService(out)

    @Test
    internal fun `display message in yellow to the console`() {
        // Arrange

        // Act
        underTest.write("myMessage")

        // Assert
        inOrder(out) {
            verify(out).print(AnsiColor.YELLOW)
            verify(out).print("myMessage")
            verify(out).print(AnsiColor.RESET)
            verify(out).println()
        }


    }
}