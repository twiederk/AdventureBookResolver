package com.d20charactersheet.adventurebookresolver.core.domain

interface BookSolver {

    fun solveBook(startPoint: BookEntry, wayPoints: List<BookEntry>): List<Solution>

}