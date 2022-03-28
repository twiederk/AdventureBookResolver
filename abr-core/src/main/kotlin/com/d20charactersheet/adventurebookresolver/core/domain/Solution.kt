package com.d20charactersheet.adventurebookresolver.core.domain

class Solution(
    val combination: Combination,
    val solutionPath: List<BookEntry>? = null,
    val invalidConnection: Connection? = null
) {
    @Suppress("BooleanMethodIsAlwaysInverted")
    fun isValid() = solutionPath != null
}
