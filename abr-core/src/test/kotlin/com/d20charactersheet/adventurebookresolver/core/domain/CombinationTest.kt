package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CombinationTest {

    @Test
    internal fun `should store list of book entries`() {
        // arrange
        val bookEntry = BookEntry(1)

        // act
        val combination = Combination(arrayOf(bookEntry))

        // assert
        assertThat(combination.wayPoints).containsExactly(bookEntry)
    }

}