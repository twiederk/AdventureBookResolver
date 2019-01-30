package com.d20charachtersheet.adventurebookresolver.core.domain

import org.jgrapht.graph.DefaultEdge

class LabeledEdge() : DefaultEdge() {
    var label: String = "unlabeled"

    constructor(label: String) : this() {
        this.label = label
    }

    override fun toString(): String {
        return label
    }


}

