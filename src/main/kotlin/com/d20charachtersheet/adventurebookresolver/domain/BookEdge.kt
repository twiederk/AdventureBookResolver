package com.d20charachtersheet.adventurebookresolver.domain

import org.jgrapht.graph.DefaultEdge

class BookEdge() : DefaultEdge() {
    var label: String = "unlabeled"

    constructor(label: String) : this() {
        this.label = label
    }

}
