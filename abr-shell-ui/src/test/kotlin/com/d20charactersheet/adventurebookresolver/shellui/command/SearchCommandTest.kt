package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class SearchCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: SearchCommand

    @Test
    fun `Search notes`() {
        // Arrange
        adventureBookResolver.book = mock {
            on { search("start") }.doReturn(listOf(com.d20charactersheet.adventurebookresolver.core.domain.BookEntry(id = 1, title = "Hallway", note = "Start of adventure")))
        }

        // Act
        underTest.search("start")

        // Assert
        verify(adventureBookResolver.book).search("start")
        verify(consoleService).write("(1) - Hallway: Start of adventure")
    }

}