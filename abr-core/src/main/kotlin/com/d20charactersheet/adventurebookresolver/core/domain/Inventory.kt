package com.d20charactersheet.adventurebookresolver.core.domain

class Inventory(gold: Int = 0, val items: MutableList<Item> = mutableListOf()) {

    var gold: Int = gold
        private set

    fun addItem(name: String) = items.add(Item(name))

    fun clear() = items.clear()

    fun removeItem(index: Int) {
        if (index < items.size && index >= 0) {
            items.removeAt(index)
        }
    }

    fun editGold(gold: Int) {
        this.gold = maxOf(this.gold + gold, 0)
    }

}

data class Item(val name: String)
