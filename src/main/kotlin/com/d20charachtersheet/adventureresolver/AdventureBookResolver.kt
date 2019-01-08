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
}

//@Test
//internal fun `create simple example graph`() {
//    // Act
//    val graph: Graph<Int, DefaultEdge> = createGraph()
//
//    // Assert
//    assertThat(graph.vertexSet()).hasSameElementsAs(setOf(1, 2, 3, 4, 5, 6))
//    assertThat(graph.toString()).isEqualTo("([1, 2, 3, 4, 5, 6], [(1,2), (1,3), (3,1), (1,4), (4,5), (4,6)])")
//}
//
//private fun createGraph(): Graph<Int, DefaultEdge> {
//    val graph: Graph<Int, DefaultEdge> = SimpleDirectedGraph(DefaultEdge::class.java)
//
//    for (i in 1..6) {
//        graph.addVertex(i)
//    }
//    graph.addEdge(1, 2)
//    graph.addEdge(1, 3)
//    graph.addEdge(3, 1)
//    graph.addEdge(1, 4)
//    graph.addEdge(4, 5)
//    graph.addEdge(4, 6)
//    return graph
//}
