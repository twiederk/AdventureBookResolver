package com.d20charachtersheet.adventurebookresolver.core.domain

import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class AttributesTest {

    @Test
    fun `create attributes with random numbers`() {
        // Act
        val underTest = Attributes(Die(Random(1)))

        // Assert
        AttributeAssert.assertThat(underTest.dexterity).name(AttributeName.DEXTERITY).value(10).maxValue(10)
        AttributeAssert.assertThat(underTest.strength).name(AttributeName.STRENGTH).value(18).maxValue(18)
        AttributeAssert.assertThat(underTest.luck).name(AttributeName.LUCK).value(7).maxValue(7)
    }

    @Test
    fun `create attributes with given numbers`() {
        // Act
        val underTest = Attributes(
                dexterity = Attribute(AttributeName.DEXTERITY, 1, 2),
                strength = Attribute(AttributeName.STRENGTH, 3, 4),
                luck = Attribute(AttributeName.LUCK, 5, 6))

        // Assert
        AttributeAssert.assertThat(underTest.dexterity).name(AttributeName.DEXTERITY).value(1).maxValue(2)
        AttributeAssert.assertThat(underTest.strength).name(AttributeName.STRENGTH).value(3).maxValue(4)
        AttributeAssert.assertThat(underTest.luck).name(AttributeName.LUCK).value(5).maxValue(6)
    }

    @Test
    fun `decrease attribute`() {
        // Arrange
        val underTest = Attributes(Die(Random(1)))

        // Act
        underTest.decrease(AttributeName.DEXTERITY, 1)

        // Assert
        AttributeAssert.assertThat(underTest.dexterity).value(9)
    }

    @Test
    fun `increase attribute`() {
        // Arrange
        val underTest = Attributes(dexterity = Attribute(AttributeName.DEXTERITY, 1, 2))

        // Act
        underTest.increase(AttributeName.DEXTERITY, 1)

        // Assert
        AttributeAssert.assertThat(underTest.dexterity).value(2)
    }

    @Test
    fun `increase not above max value`() {
        // Arrange
        val underTest = Attributes(dexterity = Attribute(AttributeName.DEXTERITY, 1, 1))

        // Act
        underTest.increase(AttributeName.DEXTERITY, 1)

        // Assert
        AttributeAssert.assertThat(underTest.dexterity).value(1)
    }
}