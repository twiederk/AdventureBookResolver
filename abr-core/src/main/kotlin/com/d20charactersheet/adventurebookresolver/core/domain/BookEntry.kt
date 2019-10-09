package com.d20charactersheet.adventurebookresolver.core.domain

data class BookEntry(val id: Int) {

    var title: String = BOOK_ENTRY_DEFAULT_TITLE
    var visit: Visit = Visit.UNVISITED
    var note: String = ""

    constructor(id: Int, title: String = BOOK_ENTRY_DEFAULT_TITLE, visit: Visit = Visit.UNVISITED, note: String = "") : this(id) {
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
