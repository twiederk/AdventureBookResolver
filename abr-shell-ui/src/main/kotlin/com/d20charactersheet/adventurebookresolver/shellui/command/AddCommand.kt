package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class AddCommand(val adventureBookResolver: AdventureBookResolver) {

    @ShellMethod("add new book entry and label action to get to this book entry")
    fun add(entryId: Int, label: String) {
        adventureBookResolver.book.addBookEntry(entryId, label)
    }

}
