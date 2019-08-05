package com.d20charachtersheet.adventurebookresolver.core.domain

import kotlin.random.Random

class Attributes(die: Die = Die(),
                 val dexterity: Attribute = Attribute(AttributeName.DEXTERITY, die.roll() + 6),
                 val strength: Attribute = Attribute(AttributeName.STRENGTH, die.roll() + die.roll() + 12),
                 val luck: Attribute = Attribute(AttributeName.LUCK, die.roll() + 6)) {

    private val attributes = mutableMapOf(
            dexterity.name to dexterity,
            strength.name to strength,
            luck.name to luck
    )

    fun increase(name: AttributeName, value: Int) {
        attributes[name]?.increase(value)
    }

    fun decrease(name: AttributeName, value: Int) {
        attributes[name]?.decrease(value)
    }

}

enum class AttributeName {
    DEXTERITY, STRENGTH, LUCK
}

class Attribute(val name: AttributeName, value: Int, maxValue: Int) {

    constructor(name: AttributeName, initValue: Int) : this(name, initValue, initValue)

    var value = value
        private set

    var maxValue = maxValue
        private set

    fun decrease(value: Int) {
        this.value -= value
    }

    fun increase(value: Int) {
        if (this.value + value <= maxValue) {
            this.value += value
        }
    }
}

class Die(private val random: Random = Random) {
    fun roll() = random.nextInt(1, 7)
}
