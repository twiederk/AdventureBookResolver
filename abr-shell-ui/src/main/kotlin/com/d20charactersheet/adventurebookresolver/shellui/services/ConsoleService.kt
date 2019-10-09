package com.d20charactersheet.adventurebookresolver.shellui.services

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

fun com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook.displayActions(consoleService: ConsoleService) {
    getActions().forEach {
        consoleService.write("${it.label} -> ${it.destination.id}")
    }
}

fun com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook.displayBookEntry(consoleService: ConsoleService) {
    com.d20charactersheet.adventurebookresolver.core.domain.BookEntry(getEntryId(), getEntryTitle(), getEntryVisit(), getEntryNote()).displayBookEntry(consoleService)
}

fun com.d20charactersheet.adventurebookresolver.core.domain.BookEntry.displayBookEntry(consoleService: ConsoleService) {
    if (note.isNotEmpty()) {
        consoleService.write("($id) - $title: $note")
    } else {
        consoleService.write("($id) - $title")
    }
}
