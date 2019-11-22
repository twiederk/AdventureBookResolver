package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.core.domain.AttributeName
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class DecreaseAttributeCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("decrease dexterity")
    fun decreaseDexterity(@ShellOption(defaultValue = "1") value: Int = 1) {
        decrease(AttributeName.DEXTERITY, value)
    }

    @ShellMethod("decrease strength")
    fun decreaseStrength(@ShellOption(defaultValue = "1") value: Int = 1) {
        decrease(AttributeName.STRENGTH, value)
    }

    @ShellMethod("decrease luck")
    fun decreaseLuck(@ShellOption(defaultValue = "1") value: Int = 1) {
        decrease(AttributeName.LUCK, value)
    }

    private fun decrease(attributeName: AttributeName, diff: Int) {
        adventureBookResolver.book.decreaseAttribute(attributeName, diff)
        with(adventureBookResolver.book.attributes.getAttribute(attributeName)) {
            consoleService.write("$name: $value / $maxValue")
        }
    }

}
