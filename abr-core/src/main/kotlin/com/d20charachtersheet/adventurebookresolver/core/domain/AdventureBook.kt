package com.d20charachtersheet.adventurebookresolver.core.domain

import org.jgrapht.Graph
import org.jgrapht.graph.SimpleDirectedGraph

class AdventureBook(val title: String) {

    private val graph: Graph<BookEntry, LabeledEdge> = SimpleDirectedGraph(LabeledEdge::class.java)
    private var currentBookEntry: BookEntry = BookEntry(1)

    init {
        graph.addVertex(currentBookEntry)
        currentBookEntry.visit = Visit.VISITED
    }

    fun editBookEntry(entryTitle: String) {
        currentBookEntry.title = entryTitle
    }

    fun getEntryTitle(): String = currentBookEntry.title

    fun addAction(label: String, id: Int) {
        val bookEntry = createOrGetBookEntry(id)
        graph.addVertex(bookEntry)
        graph.addEdge(currentBookEntry, bookEntry, LabeledEdge(label))
    }

    private fun createOrGetBookEntry(id: Int): BookEntry {
        var bookEntry = BookEntry(id)
        if (graph.vertexSet().contains(bookEntry)) {
            bookEntry = graph.vertexSet().filter { it.id == id }[0]
        }
        return bookEntry
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

    fun getActions(): Set<Action> =
            graph.outgoingEdgesOf(currentBookEntry).map { Action(it.label, graph.getEdgeTarget(it)) }.toSet()


    fun getNextBookEntries(): Set<BookEntry> = graph.outgoingEdgesOf(currentBookEntry).map { edge -> graph.getEdgeTarget(edge) }.toSet()

    fun getAllBookEntries(): Set<BookEntry> = graph.vertexSet()

}
