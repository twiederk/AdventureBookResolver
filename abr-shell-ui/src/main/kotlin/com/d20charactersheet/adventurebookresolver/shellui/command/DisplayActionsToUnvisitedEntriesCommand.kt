package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class DisplayActionsToUnvisitedEntriesCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("display actions leading to unvisited book entries")
    fun displayActionsToUnvisitedEntries() {
        adventureBookResolver.book.getActionsToUnvisitedEntries().forEach {
            consoleService.write("(${it.source.id}) - ${it.source.title}: ${it.label} -> ${it.destination.id}")
        }

    }

}
