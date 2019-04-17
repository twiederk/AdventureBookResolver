package com.d20charactersheet.adventurebookresolver.shellui.command

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NoteCommandTest : BaseCommandTest() {

    @Autowired
    lateinit var underTest: NoteCommand

    @Test
    fun `add note to current book entry`() {
        // Act
        underTest.note("myNote")

        // Assert
        Assertions.assertThat(adventureBookResolver.book.getEntryNote()).isEqualTo("myNote")
    }

}