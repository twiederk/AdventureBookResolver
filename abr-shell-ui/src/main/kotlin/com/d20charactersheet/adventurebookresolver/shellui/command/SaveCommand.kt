package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

const val EMPTY_BOOK_NAME_OPTION = ""

@ShellComponent
class SaveCommand(val adventureBookResolver: AdventureBookResolver, val bookStore: com.d20charactersheet.adventurebookresolver.core.domain.BookStore, val consoleService: ConsoleService) {

    @ShellMethod("save book to file")
    fun save(@ShellOption(defaultValue = EMPTY_BOOK_NAME_OPTION) bookNameOption: String = EMPTY_BOOK_NAME_OPTION) {
        val bookName = createBookName(bookNameOption)
        val savedBook = bookStore.save(adventureBookResolver.book, bookName)
        consoleService.write("Saved book to ${savedBook.toAbsolutePath()}")
    }

    private fun createBookName(bookNameOption: String): String {
        var bookName = bookNameOption
        if (bookNameOption == EMPTY_BOOK_NAME_OPTION) {
            bookName = adventureBookResolver.book.title
        }
        return bookName
    }

}

