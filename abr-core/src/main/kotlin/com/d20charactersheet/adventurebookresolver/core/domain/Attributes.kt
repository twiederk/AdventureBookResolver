package com.d20charactersheet.adventurebookresolver.core.domain

class Attributes(die: Die = Die(),
                 val dexterity: Attribute = Attribute(AttributeName.DEXTERITY, die.roll(DieRoll(1, 6))),
                 val strength: Attribute = Attribute(AttributeName.STRENGTH, die.roll(DieRoll(2, 12))),
                 val luck: Attribute = Attribute(AttributeName.LUCK, die.roll(DieRoll(1, 6)))) {

    private val attributes = mutableMapOf(
            dexterity.name to dexterity,
            strength.name to strength,
            luck.name to luck
    )

    fun getAttribute(attributeName: AttributeName) = attributes.getValue(attributeName)

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
        this.value = maxOf(this.value - value, MIN_VALUE)
    }

    fun increase(value: Int) {
        this.value = minOf(this.value + value, maxValue)
    }

    companion object {
        private const val MIN_VALUE = 0
    }

}

