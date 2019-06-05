package com.d20charachtersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

internal class BookRendererTest {

    private val underTest = BookRenderer()

    @Test
    fun `render simple graph and write to file`() {

        val tempDirectory = Files.createTempDirectory("abr")

        // Arrange
        val book = AdventureBook("book title").apply {
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
            editBookEntry("blue book")
            addAction("read book", 310)
            moveToBookEntry(310)
            editBookEntry("valuable information")
        }

        // Act
        val renderGraph = underTest.renderGraph(book, tempDirectory.resolve("myGraph"))

        // Assert
        assertThat(renderGraph).isEqualTo(tempDirectory.resolve("myGraph.png"))
        assertThat(renderGraph).hasBinaryContent(Files.readAllBytes(Paths.get("src/test/resources/expected_graph.png").toAbsolutePath()))

        // tear down
        Files.delete(tempDirectory.resolve(renderGraph))
    }

}