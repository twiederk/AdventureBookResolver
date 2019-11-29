package com.d20charactersheet.adventurebookresolver.core.domain

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class BookStore {

    fun save(book: AdventureBook, bookName: String = book.title): Path {
        val savedBook: Path = Paths.get("$bookName.abr")
        savedBook.toFile().writeText(export(book))
        return savedBook
    }

    fun export(book: AdventureBook): String {
        with(StringBuilder()) {
            exportBook(book)
            exportAttributes(book.attributes)
            exportInventory(book.inventory)
            exportGraph(book)
            return toString()
        }
    }

    private fun StringBuilder.exportBook(book: AdventureBook) {
        appendln("TITLE=${removeDelimiter(book.title)}")
        appendln("NOTE=${removeDelimiter(book.note)}")
        appendln("TRIES=${book.tries}")
        appendln("CURRENT_BOOK_ENTRY=${book.getEntryId()}")
    }

    private fun StringBuilder.exportAttributes(attributes: Attributes) {
        exportAttribute(attributes.dexterity)
        exportAttribute(attributes.strength)
        exportAttribute(attributes.luck)
    }

    private fun StringBuilder.exportAttribute(attribute: Attribute) {
        appendln("${attribute.name}=${attribute.value}|${attribute.maxValue}")
    }

    private fun StringBuilder.exportInventory(inventory: Inventory) {
        appendln("GOLD=${inventory.gold}")
        inventory.items.forEach { item -> appendln("ITEM=${removeDelimiter(item.name)}") }
    }

    private fun StringBuilder.exportGraph(book: AdventureBook) {
        with(book.graph) {
            vertexSet().forEach { entry -> appendln("BOOK_ENTRY=${entry.id}|${removeDelimiter(entry.title)}|${entry.visit}|${removeDelimiter(entry.note)}") }
            edgeSet().forEach { edge -> appendln("LABELED_EDGE=${getEdgeSource(edge).id}|${getEdgeTarget(edge).id}|${removeDelimiter(edge.label)}") }
        }
        book.getPerformedActions().forEach { action -> appendln("ACTION=${removeDelimiter(action.label)}|${action.source.id}|${action.destination.id}") }
    }

    private fun removeDelimiter(input: String) = input.replace('|', ' ')


    fun load(bookName: String): AdventureBook {
        val readLines = File("$bookName.abr").readLines()
        return import(readLines)
    }


    fun import(importData: List<String>): AdventureBook {
        val bookEntryMap: Map<Int, BookEntry> = importBookEntries(importData)
        val title = importTitle(importData)
        val note = importNote(importData)
        val tries = importTries(importData)
        val attributes = importAttributes(importData)
        val inventory = importInventory(importData)
        val currentBookEntryId = importCurrentBookEntry(importData)
        val labeledEdges = importLabeledEdges(importData, bookEntryMap)
        val performedActions = importActions(importData, bookEntryMap)

        return AdventureBook(title, note, tries, bookEntryMap, currentBookEntryId, labeledEdges, performedActions, inventory, attributes)
    }

    private fun importTitle(importData: List<String>): String {
        return importData[0].substring("TITLE".length + 1)
    }

    private fun importNote(importData: List<String>): String {
        return importData[1].substring("NOTE".length + 1)
    }

    private fun importTries(importData: List<String>): Int {
        return importData[2].substring("TRIES".length + 1).toInt()
    }

    private fun importCurrentBookEntry(importData: List<String>): Int {
        return importData[3].substring("CURRENT_BOOK_ENTRY".length + 1).toInt()
    }

    private fun importAttributes(importData: List<String>): Attributes {
        val dexterity = importAttribute(AttributeName.DEXTERITY, 4, importData)
        val strength = importAttribute(AttributeName.STRENGTH, 5, importData)
        val luck = importAttribute(AttributeName.LUCK, 6, importData)
        return Attributes(dexterity = dexterity, strength = strength, luck = luck)
    }

    private fun importAttribute(attributeName: AttributeName, lineIndex: Int, importData: List<String>): Attribute {
        val attributeData = importData[lineIndex].split('|')
        return Attribute(attributeName,
                attributeData[0].substring(attributeName.toString().length + 1).toInt(),
                attributeData[1].toInt())
    }

    private fun importInventory(importData: List<String>): Inventory {
        val gold = importData[7].substring("GOLD".length + 1).toInt()
        val items = importData
                .filter { s -> s.startsWith("ITEM") }
                .map { i -> Item(i.substring("ITEM".length + 1)) }
        return Inventory(gold, items.toMutableList())
    }

    private fun importActions(importData: List<String>, bookEntryMap: Map<Int, BookEntry>): List<Action> {
        return importData //
                .filter { s -> s.startsWith("ACTION") }
                .map { s -> s.split('|') }
                .map { a ->
                    Action( //
                            a[0].substring("ACTION".length + 1), //
                            bookEntryMap.getOrDefault(a[1].toInt(), BookEntry(1)), //
                            bookEntryMap.getOrDefault(a[2].toInt(), BookEntry(1)))
                }
    }

    private fun importLabeledEdges(importData: List<String>, bookEntryMap: Map<Int, BookEntry>): List<Action> {
        return importData //
                .filter { s -> s.startsWith("LABELED_EDGE") }
                .map { s -> s.split('|') }
                .map { a ->
                    Action(a[2], //
                            bookEntryMap.getOrDefault(a[0].substring("LABELED_EDGE".length + 1).toInt(), BookEntry(1)), //
                            bookEntryMap.getOrDefault(a[1].toInt(), BookEntry(1)))
                }
    }

    private fun importBookEntries(importData: List<String>): Map<Int, BookEntry> {
        val bookEntries: List<BookEntry> = importData //
                .filter { s -> s.startsWith("BOOK_ENTRY") }
                .map { s -> s.split('|') }
                .map { a -> BookEntry(a[0].substring("BOOK_ENTRY".length + 1).toInt(), a[1], Visit.valueOf(a[2]), a[3]) }
        return bookEntries.map { it.id to it }.toMap()
    }


}
