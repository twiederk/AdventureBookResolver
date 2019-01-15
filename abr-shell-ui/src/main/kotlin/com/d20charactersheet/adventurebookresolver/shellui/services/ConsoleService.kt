package com.d20charactersheet.adventurebookresolver.shellui.services

import org.springframework.stereotype.Service
import java.io.PrintStream

@Service
class ConsoleService(private val out: PrintStream) {

    constructor() : this(System.out)

    fun write(message: String) {
        with(out) {
            print(AnsiColor.YELLOW)
            print(message)
            print(AnsiColor.RESET)
            println()
        }
    }

}
