package com.d20charachtersheet.adventureresolver.domain

data class BookEntry(val id: Int) {

    var title: String = "Untitled"

    constructor(id: Int, title: String) : this(id) {
        this.title = title
    }

}

fun BookEntry.toDump(): String {
    return "BookEntry(id=$id,title=$title)"
}
