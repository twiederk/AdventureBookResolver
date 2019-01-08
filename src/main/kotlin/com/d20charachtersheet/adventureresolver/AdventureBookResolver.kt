package com.d20charachtersheet.adventureresolver

import com.d20charachtersheet.adventureresolver.domain.BookEntry
import org.jgrapht.Graph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph

class AdventureBookResolver(val title: String) {

    private val graph: Graph<BookEntry, DefaultEdge> = SimpleDirectedGraph(DefaultEdge::class.java)
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

    fun addBookEntry(id: Int) {
        val newEntry = BookEntry(id)
        graph.addVertex(newEntry)
        graph.addEdge(currentEntry, newEntry)
    }
}
