package com.d20charactersheet.adventurebookresolver.core.domain

import java.time.LocalDateTime.now
import java.util.*

class ValidationBookSolver(
    private val graphSolver: GraphSolver,
    private val bookSolverListener: BookSolverListener,
    private val factorialCalculator: FactorialCalculator = FactorialCalculator(),
    private val enableValidation: Boolean = true
) : BookSolver {

    private val solutions = mutableListOf<Solution>()
    private var numberOfCalculatedCombinations = 0L

    override fun solveBook(
        startPoint: BookEntry,
        wayPoints: List<BookEntry>
    ): List<Solution> {
        bookSolverListener.beginCalculation(now())
        bookSolverListener.maxCombinations(factorialCalculator.factorial(wayPoints.size))
        val solutions = solveFromStartPoint(startPoint, wayPoints)
        bookSolverListener.endCalculation(now())
        return solutions
    }

    private fun solveFromStartPoint(
        startPoint: BookEntry,
        wayPoints: List<BookEntry>
    ): List<Solution> {
        val concatWayPoints = concatStartPointWithWayPoints(startPoint, wayPoints)
        val solutions = when (concatWayPoints.size) {
            1 -> emptyList()
            2 -> listOf(Solution(concatWayPoints))
            else -> permute(concatWayPoints, 1)
        }
        return solutions
    }

    private fun concatStartPointWithWayPoints(startPoint: BookEntry, wayPoints: List<BookEntry>): List<BookEntry> {
        val wayPointsFromStartPoint = mutableListOf(startPoint)
        wayPointsFromStartPoint.addAll(wayPoints)
        return wayPointsFromStartPoint
    }

    fun permute(wayPoints: List<BookEntry>, startIndex: Int = 0): List<Solution> {
        for (shift in 0 until wayPoints.size - startIndex) {
            val rotatedList = wayPoints.rotateRight(shift, startIndex)

            if (enableValidation && !isCombinationValid(rotatedList, startIndex)) {
                numberOfCalculatedCombinations += factorialCalculator.factorial(wayPoints.size - (startIndex + 1))
                bookSolverListener.calculateCombinations(numberOfCalculatedCombinations)
                continue
            }

            if (startIndex + 1 < wayPoints.size - 1) {
                permute(rotatedList, startIndex + 1)
            } else {
                foundSolution(rotatedList)
            }
        }
        return solutions
    }

    private fun foundSolution(rotatedList: List<BookEntry>) {
        increaseNumberOfCalculatedCombinations()
        addSolution(rotatedList)
    }

    private fun addSolution(rotatedList: List<BookEntry>) {
        val completePathOfSolution = graphSolver.completePathOfPermutation(rotatedList)
        solutions.add(Solution(completePathOfSolution))
        bookSolverListener.foundSolution(solutions.size)
    }


    private fun increaseNumberOfCalculatedCombinations() {
        numberOfCalculatedCombinations++
        bookSolverListener.calculateCombinations(numberOfCalculatedCombinations)
    }

    private fun isCombinationValid(
        rotatedList: List<BookEntry>,
        startIndex: Int
    ): Boolean {
        for (index in 0..startIndex) {
            val startEntry = rotatedList[index]
            val endEntry = rotatedList[index + 1]
            val connection = graphSolver.calculatePath(startEntry, endEntry)
            bookSolverListener.calculatePath(startEntry, endEntry, connection?.size)
            if (connection == null) {
                return false
            }
        }
        return true
    }

}


fun <T> List<T>.rotateRight(shift: Int, startIndex: Int = 0): List<T> {
    val copy = this.toList()
    val subList = copy.subList(startIndex, copy.size)
    Collections.rotate(subList, shift)
    return copy
}

