package com.d20charachtersheet.adventurebookresolver.core.domain

data class BookEntry(val id: Int) {

    var title: String = "Untitled"
    var visit: Visit = Visit.UNVISITED

    constructor(id: Int, title: String) : this(id) {
        this.title = title
    }

    override fun toString(): String {
        return "($id) - $title"
    }


}
