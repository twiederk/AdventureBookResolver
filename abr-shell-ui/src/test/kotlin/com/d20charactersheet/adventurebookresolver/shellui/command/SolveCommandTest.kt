package com.d20charactersheet.adventurebookresolver.shellui.command

import com.d20charactersheet.adventurebookresolver.core.domain.WayMark
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.startsWith
import org.mockito.kotlin.inOrder
import org.springframework.beans.factory.annotation.Autowired

class SolveCommandTest : BaseConsoleCommandTest() {

    @Autowired
    lateinit var underTest: SolveCommand


    /**
     *                       (1 - One)
     *                       |     |
     *       to two          |     | to three
     *     ------------------      ---------------
     *     |                 to three            |
     * (2 - Two - WAP_POINT) -------> (3 - Three - WAP_POINT)
     *     |                                          |
     *     |   to four                  to four       |
     *     ---------------------   --------------------
     *                         |   |
     *                 (4 - Four - WAY_POINT)
     */
    @Test
    internal fun `should display solution to console`() {
        // arrange
        with(adventureBookResolver.book) {
            setEntryTitle("One")
            addAction("to two", 2)
            addAction("to three", 3)
            moveToBookEntry(2)
            setEntryTitle("Two")
            setEntryWayMark(WayMark.WAY_POINT)
            addAction("to four", 4)
            addAction("to three", 3)
            moveToBookEntry(4)
            setEntryTitle("Four")
            setEntryWayMark(WayMark.WAY_POINT)
            restart()
            moveToBookEntry(3)
            setEntryTitle("Three")
            addAction("to four", 4)
            setEntryWayMark(WayMark.WAY_POINT)
        }

        // act
        underTest.solve()

        // assert
        val inOrder = inOrder(consoleService)
        inOrder.verify(consoleService).write("Number of way points: 3")
        inOrder.verify(consoleService).write(startsWith("Begin of calculation: "))
        inOrder.verify(consoleService).write(startsWith("End of calculation: "))
        inOrder.verify(consoleService).write("Solution 1 of 1 with 4 entries")
        inOrder.verify(consoleService).write("(1) - One")
        inOrder.verify(consoleService).write("(2) - Two")
        inOrder.verify(consoleService).write("(3) - Three")
        inOrder.verify(consoleService).write("(4) - Four")
        inOrder.verify(consoleService).write("=====================")
        inOrder.verifyNoMoreInteractions()
    }

}