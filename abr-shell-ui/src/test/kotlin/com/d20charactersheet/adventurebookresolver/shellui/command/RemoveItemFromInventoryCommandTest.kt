package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
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
        verify(adventureBookResolver.book).removeItemFromInventory(1)
    }

}