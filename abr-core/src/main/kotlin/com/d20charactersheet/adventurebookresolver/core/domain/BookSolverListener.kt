package com.d20charactersheet.adventurebookresolver.core.domain

import java.time.LocalDateTime

interface BookSolverListener {

    fun beginCalculation(beginTime: LocalDateTime)

    fun endCalculation(endTime: LocalDateTime)

    fun calculateCombinations(numberOfCombinations: Int)

    fun calculatePath(startEntry: BookEntry, wayPoint: BookEntry, numberOfEntries: Int?)

    fun maxCombinations(maxCombinations: Int)

}