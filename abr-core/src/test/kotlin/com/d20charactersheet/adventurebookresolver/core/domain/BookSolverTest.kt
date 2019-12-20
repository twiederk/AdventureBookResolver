package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BookSolverTest {

    @Nested
    inner class CombinationsTest {

        @Test
        fun `combinations of zero way points`() {

            // Act
            val combinations = BookSolver().createCombinations(listOf())

            // Assert
            assertThat(combinations).isEmpty()
        }

        @Test
        fun `combinations of one way points`() {
            // Arrange
            val wayPoints: List<BookEntry> = listOf(BookEntry(1))

            // Act
            val combinations = BookSolver().createCombinations(wayPoints)

            // Assert
            assertThat(combinations).contains(arrayOf(BookEntry(1)))
        }

        @Test
        fun `combinations of two way points`() {
            // Arrange
            val wayPoints: List<BookEntry> = listOf(BookEntry(1), BookEntry(2))

            // Act
            val combinations = BookSolver().createCombinations(wayPoints)

            // Assert
            assertThat(combinations).containsExactlyInAnyOrder(//
                    arrayOf(BookEntry(1), BookEntry(2)),
                    arrayOf(BookEntry(2), BookEntry(1))
            )
        }

        @Test
        fun `combinations of three way points`() {
            // Arrange
            val wayPoints: List<BookEntry> = listOf(BookEntry(1), BookEntry(2), BookEntry(3))

            // Act
            val combinations = BookSolver().createCombinations(wayPoints)

            // Assert
            assertThat(combinations).containsExactlyInAnyOrder(//
                    arrayOf(BookEntry(1), BookEntry(2), BookEntry(3)),
                    arrayOf(BookEntry(1), BookEntry(3), BookEntry(2)),
                    arrayOf(BookEntry(2), BookEntry(3), BookEntry(1)),
                    arrayOf(BookEntry(2), BookEntry(1), BookEntry(3)),
                    arrayOf(BookEntry(3), BookEntry(1), BookEntry(2)),
                    arrayOf(BookEntry(3), BookEntry(2), BookEntry(1))
            )
        }

        @Test
        fun `no more than ten way points are allowed`() {
            // Arrange
            val wayPoints: List<BookEntry> = listOf( //
                    BookEntry(1), BookEntry(2), BookEntry(3), BookEntry(4), BookEntry(5), BookEntry(6), //
                    BookEntry(7), BookEntry(8), BookEntry(9), BookEntry(10), BookEntry(11)
            )

            // Act
            val throwable: Throwable = catchThrowable { BookSolver().createCombinations(wayPoints) }

            // Assert
            assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java).hasMessage("too many way points, calculation would take to long")
        }
    }

    @Nested
    inner class SolutionTest {

        @Test
        fun `zero way points with no solution`() {
            // Arrange
            val book = AdventureBook()

            // Act
            val solutions = BookSolver().solve(book.graph, book.getBookEntry(1), book.getWayPoints())

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
            val solutions = BookSolver().solve(book.graph, book.getBookEntry(1), book.getWayPoints())

            // Assert
            assertThat(solutions).hasSize(1)
            assertThat(solutions[0].map { it.title }).containsExactly("One", "Two")
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
            val solutions = BookSolver().solve(book.graph, book.getBookEntry(1), book.getWayPoints())

            // Assert
            assertThat(solutions).hasSize(1)
            assertThat(solutions[0].map { it.title }).containsExactly("One", "Two", "Three")
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
            val solutions = BookSolver().solve(book.graph, book.getBookEntry(1), book.getWayPoints())

            // Assert
            assertThat(solutions).isEmpty()
        }

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
            val solutions = BookSolver().solve(book.graph, book.getBookEntry(1), book.getWayPoints())

            // Assert
            assertThat(solutions).hasSize(1)
            assertThat(solutions[0].map { it.title }).containsExactly("One", "Two", "Three", "Four")
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
            val solutions = BookSolver().solve(book.graph, book.getBookEntry(1), book.getWayPoints())

            // Assert
            assertThat(solutions).hasSize(2)
            assertThat(solutions[0].map { it.title }).containsExactly("One", "Two", "Three", "Four")
            assertThat(solutions[1].map { it.title }).containsExactly("One", "Three", "Two", "Four")
        }

    }

}