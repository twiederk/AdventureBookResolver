package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class DisplayInventoryCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("display inventory")
    fun displayInventory() {
        with(adventureBookResolver.book) {
            consoleService.write("Inventory")
            getItems().forEachIndexed { index, item -> consoleService.write("${index + 1}> ${item.name}") }
        }
    }
}

