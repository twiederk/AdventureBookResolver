package com.d20charactersheet.adventurebookresolver.core.domain

data class Connection(
    val srcEntry: BookEntry,
    val destEntry: BookEntry
)