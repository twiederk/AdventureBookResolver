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

    fun addBookEntry(id: Int, label: String) {
        val newEntry = BookEntry(id)
        graph.addVertex(newEntry)
        graph.addEdge(currentBookEntry, newEntry, LabeledEdge(label))
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

    fun getActions(): Set<LabeledEdge> = graph.outgoingEdgesOf(currentBookEntry)

    fun getNextBookEntries(): Set<BookEntry> = getActions().map { edge -> graph.getEdgeTarget(edge) }.toSet()

    fun getAllBookEntries(): Set<BookEntry> = graph.vertexSet()

}
