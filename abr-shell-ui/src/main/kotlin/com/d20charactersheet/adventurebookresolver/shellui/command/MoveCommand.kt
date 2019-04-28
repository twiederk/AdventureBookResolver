package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.domain.displayActions
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MoveCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("move to next book entry")
    fun move(id: Int) {
        with(adventureBookResolver.book) {
            moveToBookEntry(id)
            consoleService.write("(${getEntryId()}) - ${getEntryTitle()}")
            displayActions(consoleService)
        }
    }

}
