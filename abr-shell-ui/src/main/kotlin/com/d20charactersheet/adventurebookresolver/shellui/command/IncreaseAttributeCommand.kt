package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class IncreaseAttributeCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("increase dexterity")
    fun increaseDexterity(@ShellOption(defaultValue = "1") increase: Int = 1) {
        increase(AttributeName.DEXTERITY, increase)
    }

    @ShellMethod("increase strength")
    fun increaseStrength(@ShellOption(defaultValue = "1") increase: Int = 1) {
        increase(AttributeName.STRENGTH, increase)
    }

    @ShellMethod("increase luck")
    fun increaseLuck(@ShellOption(defaultValue = "1") increase: Int = 1) {
        increase(AttributeName.LUCK, increase)
    }

    private fun increase(attributeName: AttributeName, diff: Int) {
        adventureBookResolver.book.increaseAttribute(attributeName, diff)
        with(adventureBookResolver.book.attributes.getAttribute(attributeName)) {
            consoleService.write("$name: $value / $maxValue")
        }
    }

}
