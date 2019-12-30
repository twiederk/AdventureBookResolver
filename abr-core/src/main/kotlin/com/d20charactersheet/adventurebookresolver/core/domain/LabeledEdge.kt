package com.d20charactersheet.adventurebookresolver.core.domain

import org.jgrapht.graph.DefaultEdge

class LabeledEdge(var label: String = "unlabeled") : DefaultEdge() {

    override fun toString(): String {
        return label
    }


}

