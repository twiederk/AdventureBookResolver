package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import com.d20charactersheet.adventurebookresolver.shellui.services.displayActions
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class AddCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("add action and new book entry")
    fun add(label: String, entryId: Int) {
        with(adventureBookResolver.book) {
            addAction(label, entryId)
            displayActions(consoleService)
        }
    }

}
