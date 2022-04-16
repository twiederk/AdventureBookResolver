package com.d20charactersheet.adventurebookresolver.core.domain

import org.jgrapht.Graph
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class ValidationBookSolverProgressTest {

    private val bookSolverListener: BookSolverListener = mock()

    private lateinit var graph: Graph<BookEntry, LabeledEdge>

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
        graph = book.graph
    }

    @Test
    internal fun `should emit start and end of calculation in proper order`() {

        // act
        ValidationBookSolver(graph, bookSolverListener).solveBook(
            BookEntry(1),
            listOf(BookEntry(4), BookEntry(3), BookEntry(5), BookEntry(2))
        )

        // assert
        val inOrder = inOrder(bookSolverListener)
        inOrder.verify(bookSolverListener).beginCalculation(any())
        inOrder.verify(bookSolverListener).endCalculation(any())
    }

    @Test
    internal fun `should emit max number of combinations`() {
        // act
        ValidationBookSolver(graph, bookSolverListener).solveBook(
            BookEntry(1),
            listOf(BookEntry(4), BookEntry(3), BookEntry(5), BookEntry(2))
        )

        // assert
        val inOrder = inOrder(bookSolverListener)
        inOrder.verify(bookSolverListener).maxCombinations(24)
    }

    @Test
    internal fun `should emit skipped combinations of combinations`() {
        // act
        ValidationBookSolver(graph, bookSolverListener).solveBook(
            BookEntry(1),
            listOf(BookEntry(4), BookEntry(3), BookEntry(5), BookEntry(2))
        )

        // assert
        val inOrder = inOrder(bookSolverListener)
        inOrder.verify(bookSolverListener).calculateCombinations(6)
        inOrder.verify(bookSolverListener).calculateCombinations(8)
        inOrder.verify(bookSolverListener).calculateCombinations(10)
        inOrder.verify(bookSolverListener).calculateCombinations(11)
        inOrder.verify(bookSolverListener).calculateCombinations(12)
        inOrder.verify(bookSolverListener).calculateCombinations(18)
        inOrder.verify(bookSolverListener).calculateCombinations(20)
        inOrder.verify(bookSolverListener).calculateCombinations(21)
        inOrder.verify(bookSolverListener).calculateCombinations(22)
        inOrder.verify(bookSolverListener).calculateCombinations(24)
    }

    @Test
    internal fun `should emit calculated paths`() {
        // act
        ValidationBookSolver(graph, bookSolverListener).solveBook(
            BookEntry(1),
            listOf(BookEntry(4), BookEntry(3), BookEntry(2))
        )

        // assert
        val inOrder = inOrder(bookSolverListener)
        inOrder.verify(bookSolverListener).calculatePath(BookEntry(4), BookEntry(3), null)
        inOrder.verify(bookSolverListener, times(2)).calculatePath(BookEntry(2), BookEntry(4), 2)
        inOrder.verify(bookSolverListener).calculatePath(BookEntry(4), BookEntry(3), null)
        inOrder.verify(bookSolverListener).calculatePath(BookEntry(2), BookEntry(3), 1)
        inOrder.verify(bookSolverListener).calculatePath(BookEntry(3), BookEntry(4), 1)
        inOrder.verify(bookSolverListener).calculatePath(BookEntry(3), BookEntry(2), null)
    }

}