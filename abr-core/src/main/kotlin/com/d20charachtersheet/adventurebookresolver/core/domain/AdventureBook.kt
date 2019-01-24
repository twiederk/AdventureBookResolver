package com.d20charachtersheet.adventurebookresolver.core.domain

import org.jgrapht.Graph
import org.jgrapht.graph.SimpleDirectedGraph

class AdventureBook(val title: String = "new book") {

    private val graph: Graph<BookEntry, LabeledEdge> = SimpleDirectedGraph(LabeledEdge::class.java)
    private var currentBookEntry: BookEntry = BookEntry(1)
    private val performedActions: MutableList<Action> = mutableListOf()

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
            bookEntry = getBookEntry(id)
        }
        return bookEntry
    }

    fun moveToBookEntry(id: Int) {
        val edge = graph.getEdge(currentBookEntry, BookEntry(id))
        edge?.run {
            val nextBookEntry = graph.getEdgeTarget(edge)
            nextBookEntry.visit = Visit.VISITED
            performedActions += Action(edge.label, currentBookEntry, nextBookEntry)
            currentBookEntry = nextBookEntry
        }
    }

    fun getEntryId(): Int = currentBookEntry.id

    fun getEntryVisit(): Visit = currentBookEntry.visit

    fun getActions(): Set<Action> =
            graph.outgoingEdgesOf(currentBookEntry) //
                    .map { Action(it.label, currentBookEntry, graph.getEdgeTarget(it)) } //
                    .toSet()


    fun getNextBookEntries(): Set<BookEntry> = graph.outgoingEdgesOf(currentBookEntry).map { edge -> graph.getEdgeTarget(edge) }.toSet()

    fun getAllBookEntries(): Set<BookEntry> = graph.vertexSet()

    fun getPerformedActions(): List<Action> = performedActions.toList()

    fun getPath(): List<BookEntry> {
        val path = mutableListOf<BookEntry>(getBookEntry(1))
        performedActions.forEach { path.add(it.destination) }
        return path
    }

    private fun getBookEntry(id: Int) = graph.vertexSet().filter { it.id == id }[0]

    fun getActionsToUnvisitedEntries(): List<Action> =
            graph.vertexSet() //
                    .filter { it.visit == Visit.UNVISITED } //
                    .flatMap { graph.incomingEdgesOf(it) }
                    .map { Action(it.label, graph.getEdgeSource(it), graph.getEdgeTarget(it)) }

}
