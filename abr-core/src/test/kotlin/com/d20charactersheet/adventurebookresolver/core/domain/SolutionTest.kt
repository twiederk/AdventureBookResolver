package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    internal fun `should store combination which the solution belongs to`() {
        // arrange
        val combination = Combination(arrayOf(BookEntry(1)))

        // act
        val solution = Solution(combination)

        // assert
        assertThat(solution.combination).isEqualTo(combination)
    }

    @Test
    internal fun `should store solution path when solution path is given`() {
        // arrange
        val combination = Combination(arrayOf(BookEntry(1)))
        val solutionPath = listOf(BookEntry(1))

        // act
        val solution = Solution(combination, solutionPath)

        // assert
        assertThat(solution.solutionPath).isEqualTo(solutionPath)
    }

    @Test
    internal fun `should store missing connection when missing connection is given`() {
        // arrange
        val combination = Combination(arrayOf(BookEntry(1)))
        val missingConnection = Connection(BookEntry(1), BookEntry(2))

        // act
        val solution = Solution(combination = combination, invalidConnection = missingConnection)

        // assert
        assertThat(solution.invalidConnection).isEqualTo(missingConnection)
    }

    @Test
    internal fun `should be valid if solution path is found`() {
        // arrange
        val combination = Combination(arrayOf(BookEntry(1)))
        val solutionPath = listOf(BookEntry(1))
        val solution = Solution(combination, solutionPath)

        // act
        val isValid = solution.isValid()

        // assert
        assertThat(isValid).isTrue
    }

    @Test
    internal fun `should be invalid when solution path is null`() {
        // arrange
        val combination = Combination(arrayOf(BookEntry(1)))
        val solution = Solution(combination)

        // act
        val isValid = solution.isValid()

        // assert
        assertThat(isValid).isFalse
    }


}