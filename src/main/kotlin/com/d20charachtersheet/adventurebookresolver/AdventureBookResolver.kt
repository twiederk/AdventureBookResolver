package com.d20charachtersheet.adventurebookresolver

import com.d20charachtersheet.adventurebookresolver.domain.BookEdge
import com.d20charachtersheet.adventurebookresolver.domain.BookEntry
import org.jgrapht.Graph
import org.jgrapht.graph.SimpleDirectedGraph

class AdventureBookResolver(val title: String) {

    internal val graph: Graph<BookEntry, BookEdge> = SimpleDirectedGraph(BookEdge::class.java)
    val currentEntry: BookEntry = BookEntry(1)

    init {
        graph.addVertex(currentEntry)
    }

    fun dumpGraph(): String {
        return graph.toString()
    }

    fun setEntryTitle(entryTitle: String) {
        currentEntry.title = entryTitle
    }


    fun getEntryTitle(): String = currentEntry.title

    fun addBookEntry(id: Int, label: String) {
        val newEntry = BookEntry(id)
        graph.addVertex(newEntry)
        graph.addEdge(currentEntry, newEntry, BookEdge(label))
    }
}
