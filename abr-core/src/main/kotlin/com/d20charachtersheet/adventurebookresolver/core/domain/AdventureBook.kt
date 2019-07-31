package com.d20charachtersheet.adventurebookresolver.core.domain

import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.SimpleDirectedGraph


class AdventureBook(val title: String = ADVENTURE_BOOK_DEFAULT_TITLE, val totalNumberOfBookEntries: Int = 400) {

    var tries: Int = 1
        private set
    internal val graph: Graph<BookEntry, LabeledEdge> = SimpleDirectedGraph(LabeledEdge::class.java)
    private var currentBookEntry: BookEntry = BookEntry(1)
    private val performedActions: MutableList<Action> = mutableListOf()
    private val inventory = Inventory()

    init {
        graph.addVertex(currentBookEntry)
        currentBookEntry.visit = Visit.VISITED
    }

    constructor(title: String, tries: Int, vertices: Map<Int, BookEntry>, currentBookEntryId: Int, edges: List<Action>, actions: List<Action>, items: List<Item>) : this(title) {
        this.tries = tries
        vertices.values.forEach { bookEntry -> graph.addVertex(bookEntry) }
        edges.forEach { action -> graph.addEdge(action.source, action.destination, LabeledEdge(action.label)) }
        currentBookEntry = vertices[currentBookEntryId] ?: BookEntry(1)
        performedActions += actions
        inventory.items += items
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

    fun note(note: String) {
        currentBookEntry.note = note
    }

    fun getEntryNote(): String = currentBookEntry.note

    fun restart() {
        currentBookEntry = getBookEntry(1)
        performedActions.clear()
        inventory.clear()
        tries++
    }

    fun run(entryId: Int) {
        val shortestPathAlgorithm = DijkstraShortestPath(graph)
        val path = shortestPathAlgorithm.getPath(BookEntry(1), BookEntry(entryId))

        for (bookEntry in path.vertexList) {
            moveToBookEntry(bookEntry.id)
        }
    }

    fun search(criteria: String): List<BookEntry> = graph.vertexSet().filter { it.note.contains(criteria, true) }.toList()

    fun delete(entryId: Int) {
        val bookEntry = getBookEntry(entryId)
        graph.removeEdge(currentBookEntry, bookEntry)
        if (graph.incomingEdgesOf(bookEntry).isEmpty()) {
            graph.removeVertex(bookEntry)
        }
    }

    fun getItems(): List<Item> = inventory.items

    fun addItemToInventory(name: String) = inventory.addItem(name)

    fun removeItemFromInventory(index: Int) = inventory.removeItem(index)

}
