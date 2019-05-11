package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import com.d20charactersheet.adventurebookresolver.shellui.services.displayActions
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class DisplayActionsCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("display available actions of current book entry")
    fun displayActions() = adventureBookResolver.book.displayActions(consoleService)

}

