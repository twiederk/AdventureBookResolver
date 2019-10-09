package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.AbstractAssert

class AttributeAssert(actual: Attribute) : AbstractAssert<AttributeAssert, Attribute>(actual, AttributeAssert::class.java) {

    fun value(value: Int): AttributeAssert {
        if (actual.value != value) {
            failWithMessage("Expected value to be <%s> but was <%s>", value, actual.value)
        }
        return this
    }

    fun maxValue(maxValue: Int): AttributeAssert {
        if (actual.maxValue != maxValue) {
            failWithMessage("Expected maxValue to be <%s> but was <%s>", maxValue, actual.maxValue)
        }
        return this
    }

    fun name(name: AttributeName): AttributeAssert {
        if (actual.name != name) {
            failWithMessage("Expected name to be <%s> but was <%s>", name, actual.name)
        }
        return this
    }

    fun isBetween(from: Int, until: Int): AttributeAssert {
        if (actual.value < from) {
            failWithMessage("Expected value to be higher or equal to <%s> but was <%s>", from, actual.value)
        }
        if (actual.value > until) {
            failWithMessage("Expected value to be lower or equal to <%s> but was <%s>", until, actual.value)
        }
        return this
    }


    companion object {
        fun assertThat(actual: Attribute): AttributeAssert {
            return AttributeAssert(actual)
        }
    }
}
