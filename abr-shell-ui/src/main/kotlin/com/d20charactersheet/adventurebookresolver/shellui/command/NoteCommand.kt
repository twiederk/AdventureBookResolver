package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class NoteCommand(val adventureBookResolver: AdventureBookResolver) {

    @ShellMethod("add note to book entry")
    fun note(note: String) = adventureBookResolver.book.note(note)

}
