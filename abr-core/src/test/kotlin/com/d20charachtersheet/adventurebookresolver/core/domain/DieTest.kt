package com.d20charachtersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class DieTest {

    val underTest = Die(Random(1))

    @Test
    fun `roll d6 as default`() {
        // Act
        val roll = underTest.roll()

        // Assert
        assertThat(roll).isEqualTo(4)
    }

    @Test
    fun `roll d6`() {
        // Act
        val roll = underTest.roll(DieRoll())

        // Assert
        assertThat(roll).isEqualTo(4)
    }

    @Test
    fun `roll 2d6`() {
        // Act
        val roll = underTest.roll(DieRoll(2, 0))

        // Assert
        assertThat(roll).isEqualTo(5)
    }

    @Test
    fun `roll 2d6+4`() {
        // Act
        val roll = underTest.roll(DieRoll(2, 4))

        // Assert
        assertThat(roll).isEqualTo(9)
    }

    @Test
    fun `roll 2d6-3`() {
        // Act
        val roll = underTest.roll(DieRoll(2, -3))

        // Assert
        assertThat(roll).isEqualTo(2)
    }

}