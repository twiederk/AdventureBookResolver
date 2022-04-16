package com.d20charactersheet.adventurebookresolver.shellui.command

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired

internal class IncreaseAttributeCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: IncreaseAttributeCommand

    @Test
    fun `increase dexterity by 1`() {

        // Arrange
        adventureBookResolver.book.attributes.dexterity.decrease(1)
        val expected = adventureBookResolver.book.attributes.dexterity.value + 1

        // Act
        underTest.increaseDexterity()

        // Assert
        assertThat(adventureBookResolver.book.attributes.dexterity.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.DEXTERITY}: $expected / $expected")
    }

    @Test
    fun `increase dexterity by 2`() {

        // Arrange
        adventureBookResolver.book.attributes.dexterity.decrease(2)
        val expected = adventureBookResolver.book.attributes.dexterity.value + 2

        // Act
        underTest.increaseDexterity(2)

        // Assert
        assertThat(adventureBookResolver.book.attributes.dexterity.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.DEXTERITY}: $expected / $expected")
    }

    @Test
    fun `increase strength by 1`() {

        // Arrange
        adventureBookResolver.book.attributes.strength.decrease(1)
        val expected = adventureBookResolver.book.attributes.strength.value + 1

        // Act
        underTest.increaseStrength()

        // Assert
        assertThat(adventureBookResolver.book.attributes.strength.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.STRENGTH}: $expected / $expected")
    }

    @Test
    fun `increase strength by 2`() {

        // Arrange
        adventureBookResolver.book.attributes.strength.decrease(2)
        val expected = adventureBookResolver.book.attributes.strength.value + 2

        // Act
        underTest.increaseStrength(2)

        // Assert
        assertThat(adventureBookResolver.book.attributes.strength.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.STRENGTH}: $expected / $expected")
    }

    @Test
    fun `increase luck by 1`() {

        // Arrange
        adventureBookResolver.book.attributes.luck.decrease(1)
        val expected = adventureBookResolver.book.attributes.luck.value + 1

        // Act
        underTest.increaseLuck()

        // Assert
        assertThat(adventureBookResolver.book.attributes.luck.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.LUCK}: $expected / $expected")
    }

    @Test
    fun `increase luck by 2`() {

        // Arrange
        adventureBookResolver.book.attributes.luck.decrease(2)
        val expected = adventureBookResolver.book.attributes.luck.value + 2

        // Act
        underTest.increaseLuck(2)

        // Assert
        assertThat(adventureBookResolver.book.attributes.luck.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.LUCK}: $expected / $expected")
    }


}