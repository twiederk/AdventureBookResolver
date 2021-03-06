package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import com.d20charactersheet.adventurebookresolver.shellui.services.displayBookEntry
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class DisplayPathCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {


    @ShellMethod("display adventure path")
    fun displayPath() {
        with(adventureBookResolver.book) {
            val percentage = getAllBookEntries().size / totalNumberOfBookEntries.toFloat() * 100
            consoleService.write("Number of tries: $tries")
            consoleService.write("Known book entries: ${getAllBookEntries().size} / $totalNumberOfBookEntries ($percentage%)")
            getPath().forEach { it.displayBookEntry(consoleService) }
        }
    }

}
