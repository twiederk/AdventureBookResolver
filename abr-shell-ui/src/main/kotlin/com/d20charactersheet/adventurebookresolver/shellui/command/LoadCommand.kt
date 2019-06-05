package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.BookStore
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import java.io.IOException

@ShellComponent
class LoadCommand(val adventureBookResolver: AdventureBookResolver, val bookStore: BookStore, val consoleService: ConsoleService) {

    @ShellMethod("loads book from file")
    fun load(bookName: String) {
        try {
            adventureBookResolver.book = bookStore.load(bookName)
            consoleService.write("Loaded book from $bookName")
        } catch (e: IOException) {
            consoleService.write(e.localizedMessage)
        }
    }

}
