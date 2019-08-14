package com.d20charactersheet.adventurebookresolver.shellui.converter

import com.d20charachtersheet.adventurebookresolver.core.domain.DieRoll
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class DieRollConverter : Converter<String, DieRoll> {

    override fun convert(source: String) = if (source.isEmpty()) DieRoll() else convertDieRoll(source)

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
