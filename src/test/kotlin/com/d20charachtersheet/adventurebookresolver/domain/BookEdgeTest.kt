package com.d20charachtersheet.adventurebookresolver.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BookEdgeTest {

    @Test
    internal fun `get default label of book edge`() {
        // Act
        val bookEdge = BookEdge()

        // Assert
        assertThat(bookEdge.label).isEqualTo("unlabeled")
    }

    @Test
    internal fun `create book edge with label`() {

        // Act
        val bookEdge = BookEdge("myLabel")

        // Assert
        assertThat(bookEdge.label).isEqualTo("myLabel")
    }

}