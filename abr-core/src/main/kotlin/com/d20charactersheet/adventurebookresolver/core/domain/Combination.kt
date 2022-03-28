package com.d20charactersheet.adventurebookresolver.core.domain

data class Combination(
    val wayPoints: Array<BookEntry>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Combination

        if (!wayPoints.contentEquals(other.wayPoints)) return false

        return true
    }

    override fun hashCode(): Int {
        return wayPoints.contentHashCode()
    }
}
