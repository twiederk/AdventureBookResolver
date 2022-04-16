package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.core.domain.BookSolverListener
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.d20charactersheet.adventurebookresolver.shellui.services.ConsoleService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class SolveCommand(
    val adventureBookResolver: AdventureBookResolver,
    val consoleService: ConsoleService,
    val bookSolverListener: BookSolverListener
) {

    @ShellMethod("solve book based on way points")
    fun solve() {
        consoleService.write("Number of way points: ${adventureBookResolver.book.getWayPoints().size}")
        val solutions = adventureBookResolver.book.solve(bookSolverListener)
        solutions.forEachIndexed { index, solution ->
            consoleService.write("Solution ${index + 1} of ${solutions.size} with ${solution.solutionPath.size} entries")
            solution.solutionPath.forEach { bookEntry ->
                consoleService.write("(${bookEntry.id}) - ${bookEntry.title}")
            }
            consoleService.write("=====================")
        }
    }

}
