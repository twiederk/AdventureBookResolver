package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.jgrapht.Graph
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class ValidationBookSolverSolutionTest {

    private lateinit var graph: Graph<BookEntry, LabeledEdge>

    @BeforeEach
    fun beforeEach() {
        val book = AdventureBook()
        book.addAction("to two", 2)
        book.moveToBookEntry(2)
        book.addAction("to three", 3)
        book.moveToBookEntry(3)
        book.addAction("to four", 4)
        graph = book.graph
    }

    @Test
    internal fun `should find one possible solution out of 2 elements with order is already the solution`() {


        // act
        val solutions: List<Solution> = ValidationBookSolver(graph, mock()).permute(listOf(BookEntry(1), BookEntry(2)))

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(BookEntry(1), BookEntry(2))
    }

    @Test
    internal fun `should find one possible solution out of 2 elements with order not the solution`() {

        // act
        val solutions = ValidationBookSolver(graph, mock()).permute(listOf(BookEntry(2), BookEntry(1)))

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(BookEntry(1), BookEntry(2))
    }

    @Test
    internal fun `should find one possible solution out of 3 elements with order is already the solution`() {
        // act
        val solutions = ValidationBookSolver(graph, mock()).permute(listOf(BookEntry(1), BookEntry(2), BookEntry(3)))

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(BookEntry(1), BookEntry(2), BookEntry(3))
    }

    @Test
    internal fun `should find one possible solution out of 3 elements with order is not the solution`() {
        // act
        val solutions = ValidationBookSolver(graph, mock()).permute(listOf(BookEntry(3), BookEntry(2), BookEntry(1)))

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(BookEntry(1), BookEntry(2), BookEntry(3))
    }

    @Test
    internal fun `should find one possible solution out of 4 elements with order is already the solution`() {
        // act
        val solutions =
            ValidationBookSolver(graph, mock()).permute(listOf(BookEntry(1), BookEntry(2), BookEntry(3), BookEntry(4)))

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(BookEntry(1), BookEntry(2), BookEntry(3), BookEntry(4))
    }

    @Test
    internal fun `should find one possible solution out of 4 elements with order is not the solution`() {
        // act
        val solutions =
            ValidationBookSolver(graph, mock()).permute(listOf(BookEntry(3), BookEntry(4), BookEntry(2), BookEntry(1)))

        // assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath).containsExactly(BookEntry(1), BookEntry(2), BookEntry(3), BookEntry(4))
    }
}