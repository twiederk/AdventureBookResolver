package com.d20charactersheet.adventurebookresolver.shellui.services

import com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.core.domain.BookEntry
import org.springframework.stereotype.Service
import java.io.PrintStream

@Service
class ConsoleService(private val out: PrintStream = System.out) {

    fun write(message: String) {
        with(out) {
            print(AnsiColor.YELLOW)
            print(message)
            print(AnsiColor.RESET)
            println()
        }
    }

}

fun AdventureBook.displayActions(consoleService: ConsoleService) {
    getActions().forEach {
        consoleService.write("${it.label} -> ${it.destination.id}")
    }
}

fun AdventureBook.displayBookEntry(consoleService: ConsoleService) {
    BookEntry(getEntryId(), getEntryTitle(), getEntryVisit(), getEntryWayMark(), getEntryNote()).displayBookEntry(consoleService)
}

fun BookEntry.displayBookEntry(consoleService: ConsoleService) {
    if (note.isNotEmpty()) {
        consoleService.write("($id) - $title: $note")
    } else {
        consoleService.write("($id) - $title")
    }
}
