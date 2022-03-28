package com.d20charactersheet.adventurebookresolver.core.domain

import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.GraphWalk
import java.time.LocalDateTime
import java.util.function.Function


class BookSolver(private val bookSolverListener: BookSolverListener) {

    fun solveBook(
        graph: Graph<BookEntry, LabeledEdge>,
        startPoint: BookEntry,
        wayPoints: List<BookEntry>
    ): List<Solution> {
        bookSolverListener.beginCalculation(LocalDateTime.now())
        if (wayPoints.isEmpty()) {
            return listOf()
        }

        val combinations = Combinations(wayPoints)
        bookSolverListener.maxCombinations(combinations.size())
        val solutions = solveAllCombinations(graph, combinations, startPoint)

        bookSolverListener.endCalculation(LocalDateTime.now())
        return solutions
    }

    private fun solveAllCombinations(
        graph: Graph<BookEntry, LabeledEdge>,
        combinations: Combinations,
        startPoint: BookEntry
    ): MutableList<Solution> {
        val solutions = mutableListOf<Solution>()
        val shortestPathAlgorithm = DijkstraShortestPath(graph)

        while (combinations.hasNextCombination()) {
            val combination = combinations.next()
            val solution = solveOneCombination(graph, startPoint, combination, shortestPathAlgorithm)
            if (solution.isValid()) {
                solutions.add(solution)
                combinations.remove(combination)
            } else {
                combinations.remove(checkNotNull(solution.invalidConnection))
            }
            bookSolverListener.calculateCombinations(combinations.size())
        }
        return solutions
    }

    private fun solveOneCombination(
        graph: Graph<BookEntry, LabeledEdge>,
        startPoint: BookEntry,
        combination: Combination,
        shortestPathAlgorithm: DijkstraShortestPath<BookEntry, LabeledEdge>
    ): Solution {
        var startEntry = startPoint
        var solutionPath: GraphWalk<BookEntry, LabeledEdge>? = GraphWalk(graph, listOf(startPoint), 1.0)
        val walkWeightCalculator: Function<GraphWalk<BookEntry, LabeledEdge>, Double> = Function { 1.0 }
        var missingConnection: Connection? = null

        for (wayPoint in combination.wayPoints) {
            val currentPath = shortestPathAlgorithm.getPath(startEntry, wayPoint)
            bookSolverListener.calculatePath(startEntry, wayPoint, currentPath?.length)
            if (currentPath != null) {
                solutionPath = solutionPath?.concat(currentPath as GraphWalk, walkWeightCalculator)
                startEntry = wayPoint
            } else {
                solutionPath = null
                missingConnection = Connection(startEntry, wayPoint)
                break
            }
        }

        return Solution(combination, solutionPath?.vertexList, missingConnection)
    }

}
