package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    internal fun `should store solution path when solution path is given`() {
        // arrange
        val solutionPath = listOf(BookEntry(1))

        // act
        val solution = Solution(solutionPath)

        // assert
        assertThat(solution.solutionPath).isEqualTo(solutionPath)
    }

}