package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import com.d20charactersheet.adventurebookresolver.shellui.services.displayActions
import com.d20charactersheet.adventurebookresolver.shellui.services.displayBookEntry
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MoveCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("move to next book entry")
    fun move(id: Int) {
        with(adventureBookResolver.book) {
            moveToBookEntry(id)
            displayBookEntry(consoleService)
            displayActions(consoleService)
        }
    }

}
