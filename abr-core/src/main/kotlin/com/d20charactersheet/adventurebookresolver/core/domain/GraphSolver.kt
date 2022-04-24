package com.d20charactersheet.adventurebookresolver.core.domain

import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.GraphWalk
import java.util.function.Function

class GraphSolver(
    private val graph: Graph<BookEntry, LabeledEdge>,
) {

    fun calculatePath(src: BookEntry, dest: BookEntry): MutableList<BookEntry>? {
        val shortestPathAlgorithm = DijkstraShortestPath(graph)
        val currentPath = shortestPathAlgorithm.getPath(src, dest)
        return currentPath?.vertexList
    }

    fun completePathOfPermutation(rotatedList: List<BookEntry>): List<BookEntry> {
        val shortestPathAlgorithm = DijkstraShortestPath(graph)
        var startEntry = rotatedList[0]
        var solutionPath: GraphWalk<BookEntry, LabeledEdge>? = GraphWalk(graph, listOf(startEntry), 1.0)
        val walkWeightCalculator: Function<GraphWalk<BookEntry, LabeledEdge>, Double> = Function { 1.0 }

        for (wayPoint in rotatedList.subList(1, rotatedList.size)) {
            val currentPath = shortestPathAlgorithm.getPath(startEntry, wayPoint)
            solutionPath = solutionPath?.concat(currentPath as GraphWalk, walkWeightCalculator)
            startEntry = wayPoint
        }
        return solutionPath?.vertexList ?: listOf()
    }

}