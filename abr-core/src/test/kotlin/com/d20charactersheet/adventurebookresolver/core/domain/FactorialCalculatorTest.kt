package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class FactorialCalculatorTest {

    @Test
    fun `should return 0 when factorial is called with 0`() {

        // act
        val result = FactorialCalculator().factorial(0)

        // assert
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `should return factorial result of 1 which is 1`() {

        // act
        val result = FactorialCalculator().factorial(1)

        // assert
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `should return factorial result of 2 which is 2`() {

        // act
        val result = FactorialCalculator().factorial(2)

        // assert
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `should return factorial result of 3 which is 6`() {

        // act
        val result = FactorialCalculator().factorial(3)

        // assert
        assertThat(result).isEqualTo(6)
    }

    @Test
    fun `should return factorial result of 4 which is 24`() {

        // act
        val result = FactorialCalculator().factorial(4)

        // assert
        assertThat(result).isEqualTo(24)
    }

    @Test
    fun `should return factorial result of 5 which is 120`() {

        // act
        val result = FactorialCalculator().factorial(5)

        // assert
        assertThat(result).isEqualTo(120)
    }

    @Test
    fun `should return factorial result of 10 which is 3_628_800`() {

        // act
        val result = FactorialCalculator().factorial(10)

        // assert
        assertThat(result).isEqualTo(3_628_800)
    }

    @Test
    fun `should return factorial result of 20 which is 2_432_902_008_176_640_000`() {

        // act
        val result = FactorialCalculator().factorial(20)

        // assert
        assertThat(result).isEqualTo(2_432_902_008_176_640_000)
    }
}