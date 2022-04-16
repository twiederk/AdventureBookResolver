package com.d20charactersheet.adventurebookresolver.shellui.shell

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.shell.jline.PromptProvider

internal class BookPromptProviderTest {

    @Test
    internal fun `get prompt`() {
        // Arrange
        val adventureBook = mock<com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook>()
        whenever(adventureBook.getEntryId()).thenReturn(100)
        whenever(adventureBook.getEntryTitle()).thenReturn("myTitle")
        val underTest: PromptProvider = BookPromptProvider(AdventureBookResolver(adventureBook))

        // Act
        val prompt = underTest.prompt

        // Assert
        assertThat(prompt.toString()).isEqualTo("(100) - myTitle> ")
    }
}