package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class DecreaseAttributeCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("decrease dexterity")
    fun decreaseDexterity(@ShellOption(defaultValue = "1") decrease: Int = 1) {
        decrease(adventureBookResolver.book.attributes.dexterity, decrease)
    }

    @ShellMethod("decrease strength")
    fun decreaseStrength(@ShellOption(defaultValue = "1") decrease: Int = 1) {
        decrease(adventureBookResolver.book.attributes.strength, decrease)
    }

    @ShellMethod("decrease luck")
    fun decreaseLuck(@ShellOption(defaultValue = "1") decrease: Int = 1) {
        decrease(adventureBookResolver.book.attributes.luck, decrease)
    }

    private fun decrease(attribute: com.d20charactersheet.adventurebookresolver.core.domain.Attribute, decrease: Int) {
        with(attribute) {
            decrease(decrease)
            consoleService.write("$name: $value / $maxValue")
        }
    }

}
