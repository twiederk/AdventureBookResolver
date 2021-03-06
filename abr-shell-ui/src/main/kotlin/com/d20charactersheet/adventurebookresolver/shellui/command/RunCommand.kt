package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import com.d20charactersheet.adventurebookresolver.shellui.services.displayActions
import com.d20charactersheet.adventurebookresolver.shellui.services.displayBookEntry
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class RunCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("run to existing book entry")
    fun runTo(entryId: Int) {
        with(adventureBookResolver.book) {
            run(entryId)
            displayBookEntry(consoleService)
            displayActions(consoleService)
        }
    }

}
