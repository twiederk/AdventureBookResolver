package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LabeledEdgeTest {

    @Test
    internal fun `get default label of book edge`() {
        // Act
        val edge = LabeledEdge()

        // Assert
        assertThat(edge.label).isEqualTo("unlabeled")
    }

    @Test
    internal fun `create book edge with label`() {

        // Act
        val edge = LabeledEdge("myLabel")

        // Assert
        assertThat(edge.label).isEqualTo("myLabel")
    }

    @Test
    internal fun `toString used for rendering the graph`() {
        // Act
        val toString = LabeledEdge("myLabel").toString()

        // Assert
        assertThat(toString).isEqualTo("myLabel")
    }
}