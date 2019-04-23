package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class RestartCommand(val adventureBookResolver: AdventureBookResolver) {

    @ShellMethod("Restart the book at book entry 1")
    fun restart() {
        adventureBookResolver.book.restart()
    }

}
