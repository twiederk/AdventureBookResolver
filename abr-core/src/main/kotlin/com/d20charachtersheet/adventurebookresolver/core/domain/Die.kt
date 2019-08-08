package com.d20charachtersheet.adventurebookresolver.core.domain

import kotlin.random.Random

class Die(private val random: Random = Random) {
    fun roll() = random.nextInt(1, 7)
}

