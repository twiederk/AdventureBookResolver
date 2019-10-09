package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import java.nio.file.Paths

const val DEFAULT_GRAPH_FILENAME = "graph"


@ShellComponent
class RenderGraphCommand(val adventureBookResolver: AdventureBookResolver, val bookRenderer: com.d20charactersheet.adventurebookresolver.core.domain.BookRenderer, val consoleService: ConsoleService) {

    @ShellMethod("render graph to file")
    fun renderGraph(@ShellOption(defaultValue = DEFAULT_GRAPH_FILENAME) filename: String = DEFAULT_GRAPH_FILENAME) {
        val graphPath = bookRenderer.renderGraph(adventureBookResolver.book, Paths.get(filename))
        consoleService.write("Rendered graph to ${graphPath.toAbsolutePath()}")
    }

}
