package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.Die
import com.d20charachtersheet.adventurebookresolver.core.domain.DieRoll
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class RollDieCommand(private val die: Die, private val consoleService: ConsoleService) {

    @ShellMethod("roll a six-sided die")
    fun roll(@ShellOption(defaultValue = "1d") dieRoll: DieRoll = DieRoll()) {
        consoleService.write("You rolled $dieRoll and scored: ${die.roll(dieRoll)}")
    }

}
