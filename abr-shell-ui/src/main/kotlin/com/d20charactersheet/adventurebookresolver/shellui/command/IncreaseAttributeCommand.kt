package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class IncreaseAttributeCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("increase dexterity")
    fun increaseDexterity(@ShellOption(defaultValue = "1") increase: Int = 1) {
        increase(adventureBookResolver.book.attributes.dexterity, increase)
    }

    @ShellMethod("increase strength")
    fun increaseStrength(@ShellOption(defaultValue = "1") increase: Int = 1) {
        increase(adventureBookResolver.book.attributes.strength, increase)
    }

    @ShellMethod("increase luck")
    fun increaseLuck(@ShellOption(defaultValue = "1") increase: Int = 1) {
        increase(adventureBookResolver.book.attributes.luck, increase)
    }

    private fun increase(attribute: com.d20charactersheet.adventurebookresolver.core.domain.Attribute, increase: Int) {
        with(attribute) {
            increase(increase)
            consoleService.write("$name: $value / $maxValue")
        }
    }

}
