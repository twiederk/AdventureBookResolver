package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class RemoveItemFromInventoryCommand(val adventureBookResolver: AdventureBookResolver) {

    @ShellMethod("delete item from inventory")
    fun removeItemFromInventory(index: Int) = adventureBookResolver.book.removeItemFromInventory(index - 1)

}


