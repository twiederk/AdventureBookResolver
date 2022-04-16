package com.d20charactersheet.adventurebookresolver.core.domain

import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class BookSolverListenerTest {

    @Test
    internal fun `should listen to start calculation event`() {
        // arrange
        val book = AdventureBook().apply {
            setEntryTitle("One")
            addAction("to two", 2)
            addAction("to three", 3)
            addAction("to four", 4)
            moveToBookEntry(2)
            setEntryTitle("Two")
            setEntryWayMark(WayMark.WAY_POINT)
            addAction("to four", 4)
            addAction("to three", 3)
            moveToBookEntry(4)
            setEntryTitle("Four")
            setEntryWayMark(WayMark.WAY_POINT)
            restart()
            moveToBookEntry(3)
            setEntryTitle("Three")
            addAction("to four", 4)
            setEntryWayMark(WayMark.WAY_POINT)
        }
        val bookSolverListener: BookSolverListener = mock()

        // act
        ValidationBookSolver(book.graph, bookSolverListener).solveBook(book.getBookEntry(1), book.getWayPoints())

        // assert
        verify(bookSolverListener).beginCalculation(any())
        verify(bookSolverListener).maxCombinations(6)
        verify(bookSolverListener).endCalculation(any())
    }

}