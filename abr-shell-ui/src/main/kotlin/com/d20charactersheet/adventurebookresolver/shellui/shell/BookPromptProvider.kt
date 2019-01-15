package com.d20charactersheet.adventurebookresolver.shellui.shell

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.jline.utils.AttributedString
import org.springframework.shell.jline.PromptProvider
import org.springframework.stereotype.Component

@Component
class BookPromptProvider(val adventureBookResolver: AdventureBookResolver) : PromptProvider {

    override fun getPrompt(): AttributedString {
        return AttributedString("(${adventureBookResolver.book.getEntryId()}) - ${adventureBookResolver.book.getEntryTitle()}> ")
    }

}
