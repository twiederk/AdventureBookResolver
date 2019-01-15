package com.d20charactersheet.adventurebookresolver.shellui.shell

import com.d20charachtersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.shell.jline.PromptProvider

internal class BookPromptProviderTest {

    @Test
    internal fun `get prompt`() {
        // Arrange
        val adventureBook = mock<AdventureBook>()
        whenever(adventureBook.getEntryId()).thenReturn(100)
        whenever(adventureBook.getEntryTitle()).thenReturn("myTitle")
        val underTest: PromptProvider = BookPromptProvider(AdventureBookResolver(adventureBook))

        // Act
        val prompt = underTest.prompt

        // Assert
        assertThat(prompt.toString()).isEqualTo("(100) - myTitle> ")
    }
}