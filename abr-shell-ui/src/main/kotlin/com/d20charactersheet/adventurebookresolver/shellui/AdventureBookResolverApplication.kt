package com.d20charactersheet.adventurebookresolver.shellui

import com.d20charachtersheet.adventurebookresolver.core.domain.AdventureBook
import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AdventureBookResolverApplication {

    @Bean
    fun adventureBookResolver(): AdventureBookResolver = AdventureBookResolver(AdventureBook())

}

fun main(args: Array<String>) {
    runApplication<AdventureBookResolverApplication>(*args)
}

