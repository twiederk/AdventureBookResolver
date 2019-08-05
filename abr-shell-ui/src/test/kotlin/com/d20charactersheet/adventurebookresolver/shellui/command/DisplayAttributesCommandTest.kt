package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.Attribute
import com.d20charachtersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charachtersheet.adventurebookresolver.core.domain.Attributes
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
            on { attributes }.doReturn(Attributes(
                    dexterity = Attribute(AttributeName.DEXTERITY, 9, 9),
                    strength = Attribute(AttributeName.STRENGTH, 15, 20),
                    luck = Attribute(AttributeName.LUCK, 5, 8)
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