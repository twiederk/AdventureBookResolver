package com.d20charactersheet.adventurebookresolver.shellui.command

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired

internal class RemoveItemFromInventoryCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: RemoveItemFromInventoryCommand

    @Test
    fun `add item to inventory`() {

        // Arrange
        adventureBookResolver.book = mock()

        // Act
        underTest.removeItemFromInventory(1)

        // Assert
        verify(adventureBookResolver.book).removeItemFromInventory(0)
    }

}