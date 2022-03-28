package com.d20charactersheet.adventurebookresolver.core.domain

import java.time.LocalDateTime

class EmptyBookSolverListener : BookSolverListener {

    override fun beginCalculation(beginTime: LocalDateTime) {}

    override fun endCalculation(endTime: LocalDateTime) {}

    override fun calculateCombinations(numberOfCombinations: Int) {}

    override fun calculatePath(
        startEntry: BookEntry,
        wayPoint: BookEntry,
        numberOfEntries: Int?
    ) {
    }

    override fun maxCombinations(maxCombinations: Int) {}

}
