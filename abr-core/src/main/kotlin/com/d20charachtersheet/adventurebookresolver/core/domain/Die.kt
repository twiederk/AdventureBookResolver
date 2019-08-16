package com.d20charachtersheet.adventurebookresolver.core.domain

import java.util.stream.IntStream
import kotlin.random.Random

class Die(private val random: Random = Random) {

    fun roll(dieRoll: DieRoll = DieRoll()): Int {
        return IntStream.generate { random.nextInt(1, 7) }
                .limit(dieRoll.numberOfDice.toLong())
                .sum() + dieRoll.bonus
    }

    fun roll(dieRoll: String) = roll(convert(dieRoll))

    fun convert(source: String) = if (source.isEmpty()) DieRoll() else convertDieRoll(source)

    private fun convertDieRoll(source: String): DieRoll {
        val separator = extractSeparator(source)
        val (numberOfDice, bonus) = parse(source.split(separator))
        return DieRoll(numberOfDice, bonus)
    }

    private fun extractSeparator(source: String): String = if (source.contains("d6")) "d6" else "d"

    private fun parse(input: List<String>) = Pair(parseNumberOfDice(input), parseBonus(input))

    private fun parseNumberOfDice(input: List<String>) = input[0].toInt()

    private fun parseBonus(input: List<String>) = if (input[1].isEmpty()) 0 else input[1].toInt()

}

data class DieRoll(val numberOfDice: Int = 1, val bonus: Int = 0) {

    override fun toString(): String = when (bonus) {
        0 -> "${numberOfDice}d6"
        else -> "${numberOfDice}d6${if (bonus > 0) "+" else ""}$bonus"
    }

}

