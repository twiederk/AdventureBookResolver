package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class DisplayAttributesCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: DisplayAttributesCommand

    @Test
    fun `display attributes`() {

        // Arrange
        adventureBookResolver.book = mock {
            on { attributes }.doReturn(com.d20charactersheet.adventurebookresolver.core.domain.Attributes(
                    dexterity = com.d20charactersheet.adventurebookresolver.core.domain.Attribute(com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.DEXTERITY, 9, 9),
                    strength = com.d20charactersheet.adventurebookresolver.core.domain.Attribute(com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.STRENGTH, 15, 20),
                    luck = com.d20charactersheet.adventurebookresolver.core.domain.Attribute(com.d20charactersheet.adventurebookresolver.core.domain.AttributeName.LUCK, 5, 8)
            ))
        }

        // Act
        underTest.displayAttributes()

        // Assert
        inOrder(consoleService) {
            verify(consoleService).write("DEXTERITY: 9 / 9")
            verify(consoleService).write("STRENGTH: 15 / 20")
            verify(consoleService).write("LUCK: 5 / 8")
        }
    }


}