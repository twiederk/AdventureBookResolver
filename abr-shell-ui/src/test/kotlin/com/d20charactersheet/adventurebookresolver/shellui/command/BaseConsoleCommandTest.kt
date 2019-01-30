package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.boot.test.mock.mockito.MockBean

class BaseConsoleCommandTest : BaseCommandTest() {

    @MockBean
    lateinit var consoleService: ConsoleService

}