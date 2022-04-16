package com.d20charactersheet.adventurebookresolver.shellui.services

import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import com.d20charactersheet.adventurebookresolver.core.domain.BookSolverListener
import org.springframework.stereotype.Service
import java.text.DecimalFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Service
class ConsoleBookSolverListener(val consoleService: ConsoleService) : BookSolverListener {

    val dateTimeFormatter = checkNotNull(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))
    val decimalFormat = DecimalFormat("#,###")

    var beginTime: LocalDateTime? = null

    override fun beginCalculation(beginTime: LocalDateTime) {
        this.beginTime = beginTime
        consoleService.write("Begin of calculation: ${dateTimeFormatter.format(beginTime)}")
    }

    override fun endCalculation(endTime: LocalDateTime) {
        consoleService.write("End of calculation: ${dateTimeFormatter.format(endTime)}")
        val duration = Duration.between(beginTime, endTime)
        consoleService.write("Duration: ${formatDuration(duration)}")
    }

    override fun calculateCombinations(numberOfCombinations: Long) {
        consoleService.write("Calculated combinations: ${decimalFormat.format(numberOfCombinations)}")
    }

    override fun calculatePath(startEntry: BookEntry, wayPoint: BookEntry, numberOfEntries: Int?) {
        consoleService.write("(${startEntry.id}) ${startEntry.title} -> (${wayPoint.id}) ${wayPoint.title}: [$numberOfEntries]")
    }

    override fun maxCombinations(maxCombinations: Long) {
        consoleService.write("Max. combinations: ${decimalFormat.format(maxCombinations)}")
    }

    fun formatDuration(duration: Duration): String? {
        val minutes = abs(duration.seconds) % 3600 / 60
        val seconds = abs(duration.seconds) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
