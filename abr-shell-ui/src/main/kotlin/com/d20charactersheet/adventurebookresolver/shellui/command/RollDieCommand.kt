package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.Die
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class RollDieCommand(private val die: Die = Die(), val consoleService: ConsoleService) {

    @ShellMethod("roll a six-sided die")
    fun roll() {
        consoleService.write("You rolled a ${die.roll()}")
    }

}
