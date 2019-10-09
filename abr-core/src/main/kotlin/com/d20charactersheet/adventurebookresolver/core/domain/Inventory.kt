package com.d20charactersheet.adventurebookresolver.core.domain

class Inventory(val items: MutableList<Item> = mutableListOf()) {

    fun addItem(name: String) = items.add(Item(name))

    fun clear() = items.clear()

    fun removeItem(index: Int) {
        if (index < items.size && index >= 0) {
            items.removeAt(index)
        }
    }

}

data class Item(val name: String)
