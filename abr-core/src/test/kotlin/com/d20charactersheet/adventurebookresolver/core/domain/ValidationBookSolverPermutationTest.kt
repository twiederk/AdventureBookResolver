package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ValidationBookSolverPermutationTest {

    private val graphSolver: GraphSolver = mock()
    private val validationBookSolver =
        ValidationBookSolver(graphSolver, bookSolverListener = mock(), enableValidation = false)

    @BeforeEach
    fun beforeEach() {
        whenever(graphSolver.completePathOfPermutation(any())).thenAnswer { it.arguments[0] }
    }

    @Test
    internal fun `should find permutations of 2 elements`() {

        // act
        val permutations: List<Solution> = validationBookSolver.permute(listOf(BookEntry(1), BookEntry(2)))

        // assert
        assertThat(permutations.map { it.solutionPath }).containsExactly(
            listOf(BookEntry(1), BookEntry(2)),
            listOf(BookEntry(2), BookEntry(1))
        )
    }

    @Test
    internal fun `should find permutations of 3 elements`() {

        // act
        val permutations = validationBookSolver.permute(listOf(BookEntry(1), BookEntry(2), BookEntry(3)))

        // assert
        assertThat(permutations.map { it.solutionPath }).containsExactly(
            listOf(BookEntry(1), BookEntry(2), BookEntry(3)),
            listOf(BookEntry(1), BookEntry(3), BookEntry(2)),
            listOf(BookEntry(3), BookEntry(1), BookEntry(2)),
            listOf(BookEntry(3), BookEntry(2), BookEntry(1)),
            listOf(BookEntry(2), BookEntry(3), BookEntry(1)),
            listOf(BookEntry(2), BookEntry(1), BookEntry(3)),
        )
    }

    @Test
    internal fun `should find permutations of 4 elements`() {

        // act
        val permutations =
            validationBookSolver.permute(listOf(BookEntry(1), BookEntry(2), BookEntry(3), BookEntry(4)))

        // assert
        assertThat(permutations).hasSize(24)

        assertThat(permutations.map { it.solutionPath }).containsExactly(
            listOf(BookEntry(1), BookEntry(2), BookEntry(3), BookEntry(4)),
            listOf(BookEntry(1), BookEntry(2), BookEntry(4), BookEntry(3)),
            listOf(BookEntry(1), BookEntry(4), BookEntry(2), BookEntry(3)),
            listOf(BookEntry(1), BookEntry(4), BookEntry(3), BookEntry(2)),
            listOf(BookEntry(1), BookEntry(3), BookEntry(4), BookEntry(2)),
            listOf(BookEntry(1), BookEntry(3), BookEntry(2), BookEntry(4)),

            listOf(BookEntry(4), BookEntry(1), BookEntry(2), BookEntry(3)),
            listOf(BookEntry(4), BookEntry(1), BookEntry(3), BookEntry(2)),
            listOf(BookEntry(4), BookEntry(3), BookEntry(1), BookEntry(2)),
            listOf(BookEntry(4), BookEntry(3), BookEntry(2), BookEntry(1)),
            listOf(BookEntry(4), BookEntry(2), BookEntry(3), BookEntry(1)),
            listOf(BookEntry(4), BookEntry(2), BookEntry(1), BookEntry(3)),

            listOf(BookEntry(3), BookEntry(4), BookEntry(1), BookEntry(2)),
            listOf(BookEntry(3), BookEntry(4), BookEntry(2), BookEntry(1)),
            listOf(BookEntry(3), BookEntry(2), BookEntry(4), BookEntry(1)),
            listOf(BookEntry(3), BookEntry(2), BookEntry(1), BookEntry(4)),
            listOf(BookEntry(3), BookEntry(1), BookEntry(2), BookEntry(4)),
            listOf(BookEntry(3), BookEntry(1), BookEntry(4), BookEntry(2)),

            listOf(BookEntry(2), BookEntry(3), BookEntry(4), BookEntry(1)),
            listOf(BookEntry(2), BookEntry(3), BookEntry(1), BookEntry(4)),
            listOf(BookEntry(2), BookEntry(1), BookEntry(3), BookEntry(4)),
            listOf(BookEntry(2), BookEntry(1), BookEntry(4), BookEntry(3)),
            listOf(BookEntry(2), BookEntry(4), BookEntry(1), BookEntry(3)),
            listOf(BookEntry(2), BookEntry(4), BookEntry(3), BookEntry(1)),
        )
    }

    @Test
    internal fun `should find permutations of 5 elements`() {

        // act
        val permutations =
            validationBookSolver.permute(
                listOf(BookEntry(1), BookEntry(2), BookEntry(3), BookEntry(4), BookEntry(5))
            )

        // assert
        assertThat(permutations).hasSize(120)
    }

    @Test
    internal fun `should rotate list 1 element to the right`() {
        // act
        val result = listOf(BookEntry(1), BookEntry(2), BookEntry(3)).rotateRight(1)

        // assert
        assertThat(result).containsExactly(BookEntry(3), BookEntry(1), BookEntry(2))
    }

    @Test
    internal fun `should rotate list 1 element to the right starting from the 2nd element`() {
        // act
        val result = listOf(BookEntry(1), BookEntry(2), BookEntry(3)).rotateRight(1, 1)

        // assert
        assertThat(result).containsExactly(BookEntry(1), BookEntry(3), BookEntry(2))

    }
}