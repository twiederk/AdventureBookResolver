package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class EditCommand(val adventureBookResolver: AdventureBookResolver) {

    @ShellMethod("edit title of current book entry")
    fun edit(title: String) = adventureBookResolver.book.setEntryTitle(title)

}
