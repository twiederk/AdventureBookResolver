package com.d20charactersheet.adventurebookresolver.shellui.command

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired

internal class AddItemToInventoryCommandTest : BaseCommandTest() {

    @Autowired
    lateinit var underTest: AddItemToInventoryCommand

    @Test
    fun `add item to inventory`() {

        // Arrange
        adventureBookResolver.book = mock()

        // Act
        underTest.addItemToInventory("sword")

        // Assert
        verify(adventureBookResolver.book).addItemToInventory("sword")
    }

}