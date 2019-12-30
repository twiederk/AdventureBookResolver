package com.d20charactersheet.adventurebookresolver.shellui

import com.d20charactersheet.adventurebookresolver.shellui.domain.AdventureBookResolver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AdventureBookResolverApplication {

    @Bean
    fun adventureBookResolver() = AdventureBookResolver(com.d20charactersheet.adventurebookresolver.core.domain.AdventureBook())

    @Bean
    fun bookStore() = com.d20charactersheet.adventurebookresolver.core.domain.BookStore()

    @Bean
    fun bookRenderer() = com.d20charactersheet.adventurebookresolver.shellui.render.BookRenderer()

    @Bean
    fun die() = com.d20charactersheet.adventurebookresolver.core.domain.Die()

}

fun main(args: Array<String>) {
    runApplication<AdventureBookResolverApplication>(*args)
}

