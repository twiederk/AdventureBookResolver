package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.nio.file.Files
import java.nio.file.Paths

class SaveCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: SaveCommand

    @Test
    internal fun `save book to file`() {

        // Act
        underTest.save("savedBook")

        // Assert
        argumentCaptor<String> {
            verify(consoleService).write(capture())
            Assertions.assertThat(firstValue).startsWith("Saved book to ").endsWith("savedBook.abr")
        }

        // teardown
        Files.delete(Paths.get("savedBook.abr"))
    }

    @Test
    internal fun `save book with default title`() {

        // Act
        underTest.save()

        // Assert
        argumentCaptor<String> {
            verify(consoleService).write(capture())
            Assertions.assertThat(firstValue).startsWith("Saved book to ").endsWith("new book.abr")
        }

        // teardown
        Files.delete(Paths.get("new book.abr"))
    }
}