package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class RenderGraphCommand(val adventureBookResolver: AdventureBookResolver, val consoleService: ConsoleService) {

    @ShellMethod("render graph to file")
    fun renderGraph() {
        val graphPath = adventureBookResolver.book.renderGraph()
        consoleService.write("Rendered graph to ${graphPath.toAbsolutePath()}")
    }

}
