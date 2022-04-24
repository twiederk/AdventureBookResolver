package com.d20charactersheet.adventurebookresolver.shellui.services

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class ConsoleBookSolverListenerTest {

    private val consoleService: ConsoleService = mock()
    private val consoleBookSolverListener = ConsoleBookSolverListener(consoleService)

    @Test
    internal fun `should write date and time of begin of calculation to console`() {

        // act
        consoleBookSolverListener.beginCalculation(LocalDateTime.of(2022, 3, 29, 6, 15))

        // assert
        verify(consoleService).write("Begin of calculation: 06:15 29.03.2022")
    }

    @Test
    internal fun `should write date and time of end and duration of calculation to console`() {
        // arrange
        consoleBookSolverListener.beginCalculation(LocalDateTime.of(2022, 3, 29, 6, 15))

        // act
        consoleBookSolverListener.endCalculation(LocalDateTime.of(2022, 3, 29, 6, 20, 17))

        // assert
        verify(consoleService).write("End of calculation: 06:20 29.03.2022")
        verify(consoleService).write("Duration: 05:17")
    }

    @Test
    internal fun `should display max number of combinations to console`() {

        // act
        consoleBookSolverListener.maxCombinations(10000)

        // assert
        verify(consoleService).write("Max. combinations: 10.000")

    }

    @Test
    internal fun `should display number of combinations to console`() {

        // act
        consoleBookSolverListener.calculateCombinations(2010)

        // assert
        verify(consoleService).write("Calculated combinations: 2.010")
    }

    @Test
    internal fun `should display calculated path`() {

        // act
        consoleBookSolverListener.calculatePath(
            startEntry = BookEntry(1, "Start entry"),
            wayPoint = BookEntry(2, "Way Point"),
            2
        )

        // assert
        verify(consoleService).write("(1) Start entry -> (2) Way Point: [2]")
    }

    @Test
    internal fun `should display number of found solutions`() {

        // act
        consoleBookSolverListener.foundSolution(3)

        // assert
        verify(consoleService).write("Found solution(s): 3")

    }
}