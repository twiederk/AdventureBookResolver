package com.d20charactersheet.adventurebookresolver.shellui.services

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.BookSolverListener
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Service
class ConsoleBookSolverListener(val consoleService: ConsoleService) : BookSolverListener {

    val formatter = checkNotNull(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))

    var beginTime: LocalDateTime? = null

    override fun beginCalculation(beginTime: LocalDateTime) {
        this.beginTime = beginTime
        consoleService.write("Begin of calculation: ${formatter.format(beginTime)}")
    }

    override fun endCalculation(endTime: LocalDateTime) {
        consoleService.write("End of calculation: ${formatter.format(endTime)}")
        val duration = Duration.between(beginTime, endTime)
        consoleService.write("Duration: ${formatDuration(duration)}")
    }

    override fun calculateCombinations(numberOfCombinations: Int) {
        consoleService.write("Remaining combinations: $numberOfCombinations")
    }

    override fun calculatePath(startEntry: BookEntry, wayPoint: BookEntry, numberOfEntries: Int?) {
        consoleService.write("(${startEntry.id}) ${startEntry.title} -> (${wayPoint.id}) ${wayPoint.title}: [$numberOfEntries]")
    }

    override fun maxCombinations(maxCombinations: Int) {
        consoleService.write("Max. combinations: $maxCombinations")
    }

    fun formatDuration(duration: Duration): String? {
        val minutes = abs(duration.seconds) % 3600 / 60
        val seconds = abs(duration.seconds) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
