package com.d20charactersheet.adventurebookresolver.shellui.shell

import com.d20charachtersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.shell.jline.PromptProvider

internal class BookPromptProviderTest {

    @Test
    internal fun `get prompt`() {
        // Arrange
        val underTest: PromptProvider = BookPromptProvider(AdventureBookResolver(AdventureBook("new book")))

        // Act
        val prompt = underTest.prompt

        // Assert
        assertThat(prompt.toString()).isEqualTo("new book>")
    }
}