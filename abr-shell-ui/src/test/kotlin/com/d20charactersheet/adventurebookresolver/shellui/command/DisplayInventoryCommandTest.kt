package com.d20charactersheet.adventurebookresolver.shellui.command

import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired

internal class DisplayInventoryCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: DisplayInventoryCommand

    @Test
    fun `display inventory`() {

        // Arrange
        adventureBookResolver.book = mock {
            on { getItems() }.doReturn(listOf(com.d20charactersheet.adventurebookresolver.core.domain.Item("sword"), com.d20charactersheet.adventurebookresolver.core.domain.Item("leather armor"), com.d20charactersheet.adventurebookresolver.core.domain.Item("backpack")))
        }

        // Act
        underTest.displayInventory()

        // Assert
        inOrder(consoleService) {
            verify(consoleService).write("Inventory")
            verify(consoleService).write("1> sword")
            verify(consoleService).write("2> leather armor")
            verify(consoleService).write("3> backpack")
        }
    }


}