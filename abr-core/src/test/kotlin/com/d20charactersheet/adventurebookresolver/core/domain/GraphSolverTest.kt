package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GraphSolverTest {

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
        graphSolver = GraphSolver(book.graph)
    }


    @Test
    internal fun `should return null when there is now connection between the two entries`() {

        // act
        val path = graphSolver.calculatePath(BookEntry(4), BookEntry(2))

        // assert
        assertThat(path).isNull()
    }

    @Test
    internal fun `should return path with all entries between the two entries`() {
        // act
        val path = graphSolver.calculatePath(BookEntry(2), BookEntry(4))

        // assert
        assertThat(path).containsExactly(
            BookEntry(2),
            BookEntry(3),
            BookEntry(4)
        )
    }

    @Test
    internal fun `should return path with all entries containing all entries of the permutation`() {
        // act
        val path = graphSolver.completePathOfPermutation(listOf(BookEntry(1), BookEntry(3), BookEntry(6)))

        // assert
        assertThat(path).containsExactly(
            BookEntry(1),
            BookEntry(2),
            BookEntry(3),
            BookEntry(4),
            BookEntry(5),
            BookEntry(6)
        )
    }

}