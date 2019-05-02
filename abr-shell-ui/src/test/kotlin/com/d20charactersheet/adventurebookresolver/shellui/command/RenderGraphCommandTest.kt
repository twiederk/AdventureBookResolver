package com.d20charactersheet.adventurebookresolver.shellui.command

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.nio.file.Files
import java.nio.file.Paths

internal class RenderGraphCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: RenderGraphCommand

    @Test
    fun `render graph to file`() {
        // Arrange
        with(adventureBookResolver.book) {
            editBookEntry("Hallway")
            addAction("upstairs", 100)
            addAction("downstairs", 110)
            moveToBookEntry(100)
            editBookEntry("Library")
            addAction("take book", 200)
            addAction("downstairs", 1)
            moveToBookEntry(200)
            editBookEntry("Books")
            addAction("take red book", 210)
            addAction("take blue book", 300)
            moveToBookEntry(210)
            editBookEntry("Poisoned book")
            restart()
            run(300)
            addAction("read book", 310)
            moveToBookEntry(310)
            editBookEntry("valuable information")
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