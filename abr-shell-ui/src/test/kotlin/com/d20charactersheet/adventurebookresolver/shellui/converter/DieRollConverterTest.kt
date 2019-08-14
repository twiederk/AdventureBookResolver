package com.d20charactersheet.adventurebookresolver.shellui.converter

import com.d20charachtersheet.adventurebookresolver.core.domain.DieRoll
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class DieRollConverterTest {

    private val underTest = DieRollConverter()

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