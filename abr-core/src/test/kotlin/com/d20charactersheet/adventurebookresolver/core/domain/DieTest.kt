package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class DieTest {

    val underTest = Die(Random(1))

    @Nested
    inner class DieRollTest {

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

    @Nested
    inner class ConvertDieRollTest {
        @Test
        fun `convert default die`() {
            // Act
            val dieRoll = underTest.convert("")

            // Assert
            assertThat(dieRoll).isEqualTo(DieRoll(1, 0))
        }

        @Test
        fun `convert without bonus`() {
            // Act
            val dieRoll = underTest.convert("1d")

            // Assert
            assertThat(dieRoll).isEqualTo(DieRoll(1, 0))
        }

        @Test
        fun `convert with positive bonus`() {
            // Act
            val dieRoll = underTest.convert("2d6+4")

            // Assert
            assertThat(dieRoll).isEqualTo(DieRoll(2, 4))
        }

        @Test
        fun `convert with negative bonus`() {
            // Act
            val dieRoll = underTest.convert("3d6-2")

            // Assert
            assertThat(dieRoll).isEqualTo(DieRoll(3, -2))
        }

        @Test
        fun `convert with d as separator`() {
            // Act
            val dieRoll = underTest.convert("1d+2")

            // Assert
            assertThat(dieRoll).isEqualTo(DieRoll(1, 2))
        }
    }

}