package com.d20charachtersheet.adventurebookresolver.core.domain

data class BookEntry(val id: Int) {

    var title: String = "Untitled"
    var visit: Visit = Visit.UNVISITED
    var note: String = ""

    constructor(id: Int, title: String = "Untitled", visit: Visit = Visit.UNVISITED, note: String = "") : this(id) {
        this.title = title
        this.visit = visit
        this.note = note
    }

    override fun toString(): String {
        if (note.isEmpty()) {
            return "($id) - $title"
        }
        return """($id) - $title
            |$note
        """.trimMargin()
    }


}
