package com.d20charachtersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class BookRendererTest {

    private val underTest = BookRenderer()

    @Test
    internal fun `render simple graph`() {
        // Arrange
        val book = AdventureBook("book title")
        with(book) {
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
        val renderGraph = underTest.renderGraph(book)

        // Assert
        Assertions.assertThat(renderGraph).hasBinaryContent(Files.readAllBytes(Paths.get("src/test/resources/expected_graph.png").toAbsolutePath()))

        // tear down
        Files.delete(renderGraph)
    }

}