package com.d20charachtersheet.adventurebookresolver

import com.d20charachtersheet.adventurebookresolver.domain.BookEdge
import com.d20charachtersheet.adventurebookresolver.domain.BookEntry
import org.jgrapht.Graph
import org.jgrapht.graph.SimpleDirectedGraph

class AdventureBookResolver(val title: String) {

    internal val graph: Graph<BookEntry, BookEdge> = SimpleDirectedGraph(BookEdge::class.java)
    var currentBookEntry: BookEntry = BookEntry(1)

    init {
        graph.addVertex(currentBookEntry)
    }

    fun dumpGraph(): String {
        return graph.toString()
    }

    fun setEntryTitle(entryTitle: String) {
        currentBookEntry.title = entryTitle
    }


    fun getEntryTitle(): String = currentBookEntry.title

    fun addBookEntry(id: Int, label: String) {
        val newEntry = BookEntry(id)
        graph.addVertex(newEntry)
        graph.addEdge(currentBookEntry, newEntry, BookEdge(label))
    }

    fun moveToBookEntry(id: Int) {
        val edge = graph.getEdge(currentBookEntry, BookEntry(id))
        if (edge != null) {
            currentBookEntry = graph.getEdgeTarget(edge)
        }
    }

}
