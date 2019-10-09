package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class RollDieCommand(private val die: com.d20charactersheet.adventurebookresolver.core.domain.Die, private val consoleService: ConsoleService) {

    @ShellMethod("roll a six-sided die")
    fun roll(@ShellOption(defaultValue = "1d") dieRoll: String = "1d") {
        consoleService.write("You rolled ${die.convert(dieRoll)} and scored: ${die.roll(dieRoll)}")
    }

}
