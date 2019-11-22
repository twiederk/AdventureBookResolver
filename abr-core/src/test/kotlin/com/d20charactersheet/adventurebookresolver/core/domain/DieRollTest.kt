package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DieRollTest {

    @Test
    fun `display default`() {
        // Arrange

        // Act
        val result = DieRoll().toString()

        // Assert
        assertThat(result).isEqualTo("1d6")
    }

    @Test
    fun `display without bonus`() {
        // Arrange

        // Act
        val result = DieRoll(2, 0).toString()

        // Assert
        assertThat(result).isEqualTo("2d6")
    }

    @Test
    fun `display with positive bonus`() {
        // Arrange

        // Act
        val result = DieRoll(3, 6).toString()

        // Assert
        assertThat(result).isEqualTo("3d6+6")
    }

    @Test
    fun `display with negative bonus`() {
        // Arrange

        // Act
        val result = DieRoll(3, -6).toString()

        // Assert
        assertThat(result).isEqualTo("3d6-6")
    }
}