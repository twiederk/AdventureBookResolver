package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class DecreaseAttributeCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: DecreaseAttributeCommand

    @Test
    fun `decrease dexterity by 1`() {

        // Arrange
        val expected = adventureBookResolver.book.attributes.dexterity.value - 1

        // Act
        underTest.decreaseDexterity()

        // Assert
        assertThat(adventureBookResolver.book.attributes.dexterity.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.DEXTERITY}: $expected / ${adventureBookResolver.book.attributes.dexterity.maxValue}")
    }

    @Test
    fun `decrease dexterity by 2`() {

        // Arrange
        val expected = adventureBookResolver.book.attributes.dexterity.value - 2

        // Act
        underTest.decreaseDexterity(2)

        // Assert
        assertThat(adventureBookResolver.book.attributes.dexterity.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.DEXTERITY}: $expected / ${adventureBookResolver.book.attributes.dexterity.maxValue}")
    }

    @Test
    fun `decrease strength by 1`() {

        // Arrange
        val expected = adventureBookResolver.book.attributes.strength.value - 1

        // Act
        underTest.decreaseStrength()

        // Assert
        assertThat(adventureBookResolver.book.attributes.strength.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.STRENGTH}: $expected / ${adventureBookResolver.book.attributes.strength.maxValue}")
    }

    @Test
    fun `decrease strength by 2`() {

        // Arrange
        val expected = adventureBookResolver.book.attributes.strength.value - 2

        // Act
        underTest.decreaseStrength(2)

        // Assert
        assertThat(adventureBookResolver.book.attributes.strength.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.STRENGTH}: $expected / ${adventureBookResolver.book.attributes.strength.maxValue}")
    }

    @Test
    fun `decrease luck by 1`() {

        // Arrange
        val expected = adventureBookResolver.book.attributes.luck.value - 1

        // Act
        underTest.decreaseLuck()

        // Assert
        assertThat(adventureBookResolver.book.attributes.luck.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.LUCK}: $expected / ${adventureBookResolver.book.attributes.luck.maxValue}")
    }

    @Test
    fun `decrease luck by 2`() {

        // Arrange
        val expected = adventureBookResolver.book.attributes.luck.value - 2

        // Act
        underTest.decreaseLuck(2)

        // Assert
        assertThat(adventureBookResolver.book.attributes.luck.value).isEqualTo(expected)
        verify(consoleService).write("${com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.LUCK}: $expected / ${adventureBookResolver.book.attributes.luck.maxValue}")
    }


}