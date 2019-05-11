package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import com.d20charactersheet.adventurebookresolver.shellui.services.displayBookEntry
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class SearchCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("search notes of book entries")
    fun search(criteria: String) {
        val bookEntries = adventureBookResolver.book.search(criteria)
        bookEntries.forEach { it.displayBookEntry(consoleService) }
    }

}
