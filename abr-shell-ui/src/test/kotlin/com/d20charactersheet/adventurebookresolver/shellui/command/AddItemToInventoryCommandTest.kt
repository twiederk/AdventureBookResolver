package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
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