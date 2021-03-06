package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class CreateCommand(val adventureBookResolver: AdventureBookResolver) {

    @ShellMethod("create a new book")
    fun create(bookName: String) {
        adventureBookResolver.book = com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook(bookName)
    }

}
