package com.d20charachtersheet.adventurebookresolver.core.domain

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

}