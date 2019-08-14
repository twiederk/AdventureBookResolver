package com.d20charachtersheet.adventurebookresolver.core.domain

import java.util.stream.IntStream
import kotlin.random.Random

class Die(private val random: Random = Random) {

    fun roll(dieRoll: DieRoll = DieRoll()): Int {
        return IntStream.generate { random.nextInt(1, 7) }
                .limit(dieRoll.numberOfDice.toLong())
                .sum() + dieRoll.bonus
    }

}

data class DieRoll(val numberOfDice: Int = 1, val bonus: Int = 0) {

    override fun toString(): String = when (bonus) {
        0 -> "${numberOfDice}d6"
        else -> "${numberOfDice}d6${if (bonus > 0) "+" else ""}$bonus"
    }

}

