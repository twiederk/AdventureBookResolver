package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.core.domain.Visit
import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class RunCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: RunCommand

    @Test
    fun `Run to existing book entry`() {
        // Arrange
        adventureBookResolver.book = mock {
            on { getEntryId() }.doReturn(100)
            on { getEntryTitle() }.doReturn("Library")
            on { getEntryVisit() }.doReturn(Visit.VISITED)
            on { getEntryWayMark() }.doReturn(WayMark.WAY_POINT)
            on { getEntryNote() }.doReturn("a lot of books")
            on { getActions() }.doReturn(setOf(com.d20charactersheet.adventurebookresolver.core.domain.Action("downstairs", mock(), com.d20charactersheet.adventurebookresolver.core.domain.BookEntry(1))))
        }

        // Act
        underTest.runTo(100)

        // Assert
        verify(adventureBookResolver.book).run(100)
        verify(consoleService).write("(100) - Library: a lot of books")
        verify(consoleService).write("downstairs -> 1")
    }

}