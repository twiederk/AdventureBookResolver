package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class DisplayPathCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {


    @ShellMethod("display adventure path")
    fun displayPath() =
            adventureBookResolver.book.getPath().forEach { consoleService.write("(${it.id}) - ${it.title}") }

}
