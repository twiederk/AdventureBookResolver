package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MoveCommand(val adventureBookResolver: AdventureBookResolver) {

    @ShellMethod("move to next book entry")
    fun move(id: Int) = adventureBookResolver.book.moveToBookEntry(id)

}
