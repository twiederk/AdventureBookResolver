package com.d20charactersheet.adventurebookresolver.core.domain

import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.GraphWalk
import java.util.function.Function


class BookSolver {

    internal fun createCombinations(wayPoints: List<BookEntry>): List<Array<BookEntry>> =
            when (wayPoints.size) {
                0 -> listOf()
                1 -> listOf(wayPoints.toTypedArray())
                in 2..10 -> permute(wayPoints)
                else -> throw IllegalArgumentException("too many way points, calculation would take to long")
            }

    private fun permute(wayPoints: List<BookEntry>): List<Array<BookEntry>> {
        val solutions = mutableListOf<Array<BookEntry>>()
        permute(wayPoints.size, wayPoints.toTypedArray(), solutions)
        return solutions.toList()
    }

    private fun permute(n: Int, wayPoints: Array<BookEntry>, solutions: MutableList<Array<BookEntry>>) {
        if (n == 1) {
            solutions.add(wayPoints.copyOf())
        } else {
            for (i in 0 until n - 1) {
                permute(n - 1, wayPoints, solutions)
                if (n % 2 == 0) {
                    swap(wayPoints, i, n - 1)
                } else {
                    swap(wayPoints, 0, n - 1)
                }
            }
            permute(n - 1, wayPoints, solutions)
        }
    }

    private fun swap(input: Array<BookEntry>, a: Int, b: Int) {
        val tmp = input[a]
        input[a] = input[b]
        input[b] = tmp
    }

    fun solve(graph: Graph<BookEntry, LabeledEdge>, startPoint: BookEntry, wayPoints: List<BookEntry>): List<List<BookEntry>> {
        if (wayPoints.isEmpty()) {
            return listOf()
        }

        val shortestPathAlgorithm = DijkstraShortestPath(graph)
        val combinations = createCombinations(wayPoints)
        val solutions = mutableListOf<List<BookEntry>>()
        for (combination in combinations) {
            val solution = solve(graph, startPoint, combination.asList(), shortestPathAlgorithm)
            if (solution != null) {
                solutions.add(solution)
            }
        }
        return solutions
    }

    private fun solve(graph: Graph<BookEntry, LabeledEdge>, startPoint: BookEntry, wayPoints: List<BookEntry>, shortestPathAlgorithm: DijkstraShortestPath<BookEntry, LabeledEdge>): List<BookEntry>? {
        var startEntry = startPoint
        var solutionPath = GraphWalk<BookEntry, LabeledEdge>(graph, listOf(startPoint), 1.0)
        val walkWeightCalculator: Function<GraphWalk<BookEntry, LabeledEdge>, Double> = Function { 1.0 }

        for (wayPoint in wayPoints) {
            val currentPath = shortestPathAlgorithm.getPath(startEntry, wayPoint)
            if (currentPath != null) {
                solutionPath = solutionPath.concat(currentPath as GraphWalk, walkWeightCalculator)
                startEntry = wayPoint
            } else {
                return null
            }
        }
        return solutionPath.vertexList
    }

}
