package com.d20charactersheet.adventurebookresolver.shellui.domain

import com.d20charachtersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService

class AdventureBookResolver(var book: AdventureBook)


fun AdventureBook.displayActions(consoleService: ConsoleService) {
    getActions().forEach {
        consoleService.write("${it.label} -> ${it.entry.id}")
    }
}
