package com.d20charactersheet.adventurebookresolver.shellui.command


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired

internal class LoadCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: LoadCommand

    @Test
    fun `load book from file`() {

        // Act
        underTest.load("src/test/resources/loadBook")

        // Assert
        assertThat(adventureBookResolver.book.title).isEqualTo("load title")
        assertThat(adventureBookResolver.book.getAllBookEntries()).hasSize(3)
        assertThat(adventureBookResolver.book.getPath()).hasSize(2)

        argumentCaptor<String> {
            verify(consoleService).write(capture())
            assertThat(firstValue).startsWith("Loaded book from ").endsWith("loadBook")
        }

    }

    @Test
    fun `handle file not found`() {
        // Arrange

        // Act
        underTest.load("notExistingFile")

        // Assert
        argumentCaptor<String> {
            verify(consoleService).write(capture())
            assertThat(firstValue).startsWith("notExistingFile")
        }

    }
}