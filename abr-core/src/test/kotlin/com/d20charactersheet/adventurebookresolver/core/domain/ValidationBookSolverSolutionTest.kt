package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class ValidationBookSolverSolutionTest {

    private lateinit var graphSolver: GraphSolver

    @BeforeEach
    fun beforeEach() {
        val book = AdventureBook()
        book.addAction("to two", 2)
        book.moveToBookEntry(2)
        book.addAction("to three", 3)
        book.moveToBookEntry(3)
        book.addAction("to four", 4)
        book.moveToBookEntry(4)
        book.addAction("to five", 5)
        book.moveToBookEntry(5)
        book.addAction("to six", 6)
        book.moveToBookEntry(6)
        book.addAction("to seven", 7)
        book.moveToBookEntry(7)
        book.addAction("to eight", 8)
        graphSolver = GraphSolver(book.graph)
    }

    @Test
    internal fun `should find one possible solution out of 2 elements with order is already the solution`() {

        // arrange
        val wayPoints = listOf(BookEntry(2), BookEntry(4))

        // act
        val solutions: List<Solution> = ValidationBookSolver(graphSolver, mock()).solveBook(BookEntry(1), wayPoints)

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(
            BookEntry(1),
            BookEntry(2),
            BookEntry(3),
            BookEntry(4)
        )
    }

    @Test
    internal fun `should find one possible solution out of 2 elements with order not the solution`() {

        // arrange
        val wayPoints = listOf(BookEntry(4), BookEntry(2))

        // act
        val solutions = ValidationBookSolver(graphSolver, mock()).solveBook(BookEntry(1), wayPoints)

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(
            BookEntry(1),
            BookEntry(2),
            BookEntry(3),
            BookEntry(4)
        )
    }

    @Test
    internal fun `should find one possible solution out of 3 elements with order is already the solution`() {


        // arrange
        val wayPoints = listOf(BookEntry(2), BookEntry(4), BookEntry(6))

        // act
        val solutions = ValidationBookSolver(graphSolver, mock()).solveBook(BookEntry(1), wayPoints)

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(
            BookEntry(1),
            BookEntry(2),
            BookEntry(3),
            BookEntry(4),
            BookEntry(5),
            BookEntry(6),
        )
    }

    @Test
    internal fun `should find one possible solution out of 3 elements with order is not the solution`() {

        // arrange
        val wayPoints = listOf(BookEntry(6), BookEntry(4), BookEntry(2))

        // act
        val solutions = ValidationBookSolver(graphSolver, mock()).solveBook(BookEntry(1), wayPoints)

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(
            BookEntry(1),
            BookEntry(2),
            BookEntry(3),
            BookEntry(4),
            BookEntry(5),
            BookEntry(6),
        )
    }

    @Test
    internal fun `should find one possible solution out of 4 elements with order is already the solution`() {

        // arrange
        val wayPoints = listOf(BookEntry(2), BookEntry(4), BookEntry(6), BookEntry(8))

        // act
        val solutions = ValidationBookSolver(graphSolver, mock()).solveBook(BookEntry(1), wayPoints)

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(
            BookEntry(1),
            BookEntry(2),
            BookEntry(3),
            BookEntry(4),
            BookEntry(5),
            BookEntry(6),
            BookEntry(7),
            BookEntry(8),
        )
    }

    @Test
    internal fun `should find one possible solution out of 4 elements with order is not the solution`() {

        // arrange
        val wayPoints = listOf(BookEntry(6), BookEntry(8), BookEntry(4), BookEntry(2))

        // act
        val solutions = ValidationBookSolver(graphSolver, mock()).solveBook(BookEntry(1), wayPoints)

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(
            BookEntry(1),
            BookEntry(2),
            BookEntry(3),
            BookEntry(4),
            BookEntry(5),
            BookEntry(6),
            BookEntry(7),
            BookEntry(8),
        )
    }
}