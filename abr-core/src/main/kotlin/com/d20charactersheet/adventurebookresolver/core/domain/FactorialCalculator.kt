package com.d20charactersheet.adventurebookresolver.core.domain

class FactorialCalculator {

    fun factorial(value: Int): Long = when (value) {
        0 -> 0L
        1 -> 1L
        else -> value * factorial(value - 1)
    }

}
