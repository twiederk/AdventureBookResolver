package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.nio.file.Files
import java.nio.file.Paths

class RenderGraphCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: RenderGraphCommand

    @Test
    internal fun `render graph to file`() {
        // Arrange
        with(adventureBookResolver.book) {
            editBookEntry("Hallway")
            addAction("upstairs", 261)
            addAction("downstairs", 54)
            moveToBookEntry(261)
            editBookEntry("Library")
            addAction("take book", 100)
            addAction("downstairs", 54)
            moveToBookEntry(100)
            editBookEntry("Books")
            addAction("read first book", 200)
            addAction("read second book", 300)
        }

        // Act
        underTest.renderGraph()

        // Assert
        argumentCaptor<String> {
            verify(consoleService).write(capture())
            assertThat(firstValue).startsWith("Rendered graph to ")

            val graphPath = Paths.get(firstValue.substring("Rendered graph to ".length))
            assertThat(graphPath).hasBinaryContent(Files.readAllBytes(Paths.get("src/test/resources/expected_graph.png").toAbsolutePath()))

            // tear down
            Files.delete(graphPath)
        }

    }

}