package com.d20charactersheet.adventurebookresolver.core.domain

class Inventory(gold: Int = 0, provisions: Int = 10, val items: MutableList<Item> = mutableListOf()) {

    var provisions: Int = provisions
        private set
    var gold: Int = gold
        private set

    fun addItem(name: String) = items.add(Item(name))

    fun clear() {
        provisions = 10
        gold = 0
        items.clear()
    }

    fun removeItem(index: Int) {
        if (index < items.size && index >= 0) {
            items.removeAt(index)
        }
    }

    fun editGold(gold: Int) {
        this.gold = maxOf(this.gold + gold, 0)
    }

    fun editProvisions(provisions: Int) {
        this.provisions = maxOf(this.provisions + provisions, 0)
    }

}

data class Item(val name: String)
