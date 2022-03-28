package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BookSolverTest {

    @Test
    fun `zero way points with no solution`() {
        // Arrange
        val book = AdventureBook()

        // Act
        val solutions =
            BookSolver(EmptyBookSolverListener()).solveBook(book.graph, book.getBookEntry(1), book.getWayPoints())

        // Assert
        assertThat(solutions).isEmpty()
    }

    @Test
    fun `one way point with one solution`() {
        // Arrange
        val book = AdventureBook().apply {
            setEntryTitle("One")
            addAction("to two", 2)
            moveToBookEntry(2)
            setEntryTitle("Two")
            setEntryWayMark(WayMark.WAY_POINT)
        }

        // Act
        val solutions =
            BookSolver(EmptyBookSolverListener()).solveBook(book.graph, book.getBookEntry(1), book.getWayPoints())

        // Assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath?.map { it.title }).containsExactly("One", "Two")
    }

    @Test
    fun `two way points with one solution`() {
        // Arrange
        val book = AdventureBook().apply {
            setEntryTitle("One")
            addAction("to two", 2)
            addAction("to three", 3)
            moveToBookEntry(2)
            setEntryTitle("Two")
            setEntryWayMark(WayMark.WAY_POINT)
            addAction("to three", 3)
            moveToBookEntry(3)
            setEntryTitle("Three")
            setEntryWayMark(WayMark.WAY_POINT)
        }

        // Act
        val solutions =
            BookSolver(EmptyBookSolverListener()).solveBook(book.graph, book.getBookEntry(1), book.getWayPoints())

        // Assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath?.map { it.title }).containsExactly("One", "Two", "Three")
    }

    @Test
    fun `three way points with no solution`() {
        // Arrange
        val book = AdventureBook().apply {
            setEntryTitle("One")
            addAction("to two", 2)
            addAction("to three", 3)
            addAction("to four", 4)
            moveToBookEntry(2)
            setEntryTitle("Two")
            setEntryWayMark(WayMark.WAY_POINT)
            addAction("to four", 4)
            moveToBookEntry(4)
            setEntryTitle("Four")
            setEntryWayMark(WayMark.WAY_POINT)
            restart()
            moveToBookEntry(3)
            setEntryTitle("Three")
            addAction("to four", 4)
            setEntryWayMark(WayMark.WAY_POINT)
        }

        // Act
        val solutions =
            BookSolver(EmptyBookSolverListener()).solveBook(book.graph, book.getBookEntry(1), book.getWayPoints())

        // Assert
        assertThat(solutions).isEmpty()
    }

    /**
     *                       (1 - One)
     *                       |     |
     *       to two          |     | to three
     *     ------------------      ---------------
     *     |                 to three            |
     * (2 - Two - WAP_POINT) -------> (3 - Three - WAP_POINT)
     *     |                                          |
     *     |   to four                  to four       |
     *     ---------------------   --------------------
     *                         |   |
     *                      (4 - Four - WAY_POINT)
     */
    @Test
    fun `three way points with one solution`() {
        // Arrange
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

        // Act
        val solutions =
            BookSolver(EmptyBookSolverListener()).solveBook(book.graph, book.getBookEntry(1), book.getWayPoints())

        // Assert
        assertThat(solutions).hasSize(1)
        assertThat(solutions[0].solutionPath?.map { it.title }).containsExactly("One", "Two", "Three", "Four")
    }

    @Test
    fun `three way points with two solution`() {
        // Arrange
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
            addAction("to two", 2)
            addAction("to four", 4)
            setEntryWayMark(WayMark.WAY_POINT)
        }

        // Act
        val solutions =
            BookSolver(EmptyBookSolverListener()).solveBook(book.graph, book.getBookEntry(1), book.getWayPoints())

        // Assert
        assertThat(solutions).hasSize(2)
        assertThat(solutions[0].solutionPath?.map { it.title }).containsExactly("One", "Two", "Three", "Four")
        assertThat(solutions[1].solutionPath?.map { it.title }).containsExactly("One", "Three", "Two", "Four")
    }

}