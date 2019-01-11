package com.d20charachtersheet.adventurebookresolver

import com.d20charachtersheet.adventurebookresolver.domain.BookEdge
import com.d20charachtersheet.adventurebookresolver.domain.BookEntry
import com.d20charachtersheet.adventurebookresolver.domain.Visit
import org.jgrapht.Graph
import org.jgrapht.graph.SimpleDirectedGraph

class AdventureBookResolver(val title: String) {

    private val graph: Graph<BookEntry, BookEdge> = SimpleDirectedGraph(BookEdge::class.java)
    private var currentBookEntry: BookEntry = BookEntry(1)

    init {
        graph.addVertex(currentBookEntry)
        currentBookEntry.visit = Visit.VISITED
    }

    fun dumpGraph(): String {
        return graph.toString()
    }

    fun editBookEntry(entryTitle: String) {
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
        edge?.run {
            currentBookEntry = graph.getEdgeTarget(edge)
            currentBookEntry.visit = Visit.VISITED
        }
    }

    fun getEntryId(): Int = currentBookEntry.id

    fun getEntryVisit(): Visit = currentBookEntry.visit

    fun getEdges(): Set<BookEdge> = graph.outgoingEdgesOf(currentBookEntry)

    fun getBookEntries(): Set<BookEntry> = getEdges().map { edge -> graph.getEdgeTarget(edge) }.toSet()

}
