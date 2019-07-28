package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class AddItemToInventoryCommand(val adventureBookResolver: AdventureBookResolver) {

    @ShellMethod("add item to inventory")
    fun addItemToInventory(name: String) = adventureBookResolver.book.addItemToInventory(name)

}


