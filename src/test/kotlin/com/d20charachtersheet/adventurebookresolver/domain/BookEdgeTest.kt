package com.d20charachtersheet.adventurebookresolver.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BookEdgeTest {

    @Test
    internal fun `get default label of book edge`() {
        // Act
        val edge = BookEdge()

        // Assert
        assertThat(edge.label).isEqualTo("unlabeled")
    }

    @Test
    internal fun `create book edge with label`() {

        // Act
        val edge = BookEdge("myLabel")

        // Assert
        assertThat(edge.label).isEqualTo("myLabel")
    }

}