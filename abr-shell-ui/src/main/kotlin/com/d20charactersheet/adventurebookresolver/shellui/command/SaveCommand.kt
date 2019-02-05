package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charachtersheet.adventurebookresolver.core.domain.BookStore
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

const val EMPTY_FILENAME_OPTION = ""

@ShellComponent
class SaveCommand(val adventureBookResolver: AdventureBookResolver, val bookStore: BookStore, val consoleService: ConsoleService) {

    @ShellMethod("save book to file")
    fun save(@ShellOption(defaultValue = EMPTY_FILENAME_OPTION) filenameOption: String = EMPTY_FILENAME_OPTION) {
        val filename = createFilename(filenameOption)
        val savedBook = bookStore.save(adventureBookResolver.book, filename)
        consoleService.write("Saved book to ${savedBook.toAbsolutePath()}")
    }

    private fun createFilename(filenameOption: String): String {
        var filename = filenameOption
        if (filenameOption == EMPTY_FILENAME_OPTION) {
            filename = adventureBookResolver.book.title
        }
        return filename
    }

}

