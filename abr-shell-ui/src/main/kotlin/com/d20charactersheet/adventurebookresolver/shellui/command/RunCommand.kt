package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.domain.displayActions
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class RunCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("run to existing book entry")
    fun runTo(entryId: Int) {
        with(adventureBookResolver.book) {
            run(entryId)
            consoleService.write("(${getEntryId()}) - ${getEntryTitle()}")
            displayActions(consoleService)
        }
    }

}
