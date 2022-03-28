package com.d20charactersheet.adventurebookresolver.core.domain

@Suppress("BooleanMethodIsAlwaysInverted", "BooleanMethodIsAlwaysInverted")
class Combinations(wayPoints: List<BookEntry>) {

    var combinations = mutableListOf<Combination>()

    init {
        combinations.addAll(createCombinations(wayPoints))
    }

    private fun createCombinations(wayPoints: List<BookEntry>): MutableList<Combination> =
        when (wayPoints.size) {
            0 -> mutableListOf()
            1 -> mutableListOf(Combination(wayPoints.toTypedArray()))
            in 2..10 -> permute(wayPoints)
            else -> throw IllegalArgumentException("too many way points, calculation would take to long")
        }

    private fun permute(wayPoints: List<BookEntry>): MutableList<Combination> {
        val solutions = mutableListOf<Combination>()
        permute(wayPoints.size, wayPoints.toTypedArray(), solutions)
        return solutions
    }

    private fun permute(n: Int, wayPoints: Array<BookEntry>, solutions: MutableList<Combination>) {
        if (n == 1) {
            solutions.add(Combination(wayPoints.copyOf()))
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

    fun remove(invalidConnection: Connection) {
        combinations = combinations.filterNot { combination ->
            val connections = combination.wayPoints.toList()
                .windowed(size = 2, step = 1)
                .map { Connection(it[0], it[1]) }
            containsInvalidConnection(connections, invalidConnection)
        }.toMutableList()
    }

    fun remove(combination: Combination) {
        combinations.remove(combination)
    }

    private fun containsInvalidConnection(
        connections: List<Connection>,
        invalidConnection: Connection
    ): Boolean {
        for (connection in connections) {
            if (connection == invalidConnection) {
                return true
            }
        }
        return false
    }

    fun isEmpty() = combinations.isEmpty()

    fun size() = combinations.size

    @Suppress("BooleanMethodIsAlwaysInverted")
    fun hasNextCombination() = combinations.isNotEmpty()

    fun next(): Combination {
        if (combinations.isEmpty()) {
            throw IllegalStateException("No more combinations available")
        }
        return combinations[0]
    }

}
