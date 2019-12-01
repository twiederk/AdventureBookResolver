package com.d20charactersheet.adventurebookresolver.core.domain

import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.SimpleDirectedGraph


class AdventureBook(
        val title: String = ADVENTURE_BOOK_DEFAULT_TITLE,
        var note: String = "",
        tries: Int = 1,
        vertices: Map<Int, BookEntry> = mapOf(),
        currentBookEntryId: Int = 1,
        edges: List<Action> = listOf(),
        actions: List<Action> = listOf(),
        inventory: Inventory = Inventory(),
        attributes: Attributes = Attributes()) {

    val totalNumberOfBookEntries: Int = 400
    var tries = tries
        private set
    var attributes = attributes
        private set
    var inventory = inventory
        private set
    internal val graph: Graph<BookEntry, LabeledEdge> = SimpleDirectedGraph(LabeledEdge::class.java)
    private var currentBookEntry: BookEntry
    private val performedActions: MutableList<Action> = mutableListOf()

    init {
        vertices.values.forEach { bookEntry -> graph.addVertex(bookEntry) }
        currentBookEntry = vertices[currentBookEntryId] ?: BookEntry(1)
        graph.addVertex(currentBookEntry)
        currentBookEntry.visit = Visit.VISITED
        edges.forEach { action -> graph.addEdge(action.source, action.destination, LabeledEdge(action.label)) }
        performedActions += actions
    }

    fun setEntryTitle(entryTitle: String) {
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

    fun setEntryNote(note: String) {
        currentBookEntry.note = note
    }

    fun getEntryNote(): String = currentBookEntry.note

    fun restart() {
        currentBookEntry = getBookEntry(1)
        performedActions.clear()
        inventory.clear()
        attributes = Attributes()
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

    fun increaseAttribute(attributeName: AttributeName, value: Int) {
        attributes.increase(attributeName, value)
    }

    fun decreaseAttribute(attributeName: AttributeName, value: Int) {
        attributes.decrease(attributeName, value)
    }

    fun getGold(): Int = inventory.gold

    fun editGold(gold: Int) = inventory.editGold(gold)

}
