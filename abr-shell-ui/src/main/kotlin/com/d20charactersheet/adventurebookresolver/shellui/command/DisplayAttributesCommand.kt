package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class DisplayAttributesCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("display attributes")
    fun displayAttributes() {
        with(adventureBookResolver.book.attributes) {
            consoleService.write("${dexterity.name}: ${dexterity.value} / ${dexterity.maxValue}")
            consoleService.write("${strength.name}: ${strength.value} / ${strength.maxValue}")
            consoleService.write("${luck.name}: ${luck.value} / ${luck.maxValue}")
        }
    }

}
