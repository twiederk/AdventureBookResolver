package com.d20charachtersheet.adventurebookresolver.core.domain

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class BookStore {

    fun save(book: AdventureBook, filename: String = book.title): Path {
        val savedBook: Path = Paths.get("$filename.abr")
        savedBook.toFile().writeText(export(book))
        return savedBook
    }

    fun export(book: AdventureBook): String {
        with(StringBuilder()) {
            appendln("TITLE=${book.title}")
            appendln("CURRENT_BOOK_ENTRY=${book.getEntryId()}")
            with(book.graph) {
                vertexSet().forEach { entry -> appendln("BOOK_ENTRY=${entry.id},${entry.title},${entry.visit},${entry.note}") }
                edgeSet().forEach { edge -> appendln("LABELED_EDGE=${getEdgeSource(edge).id},${getEdgeTarget(edge).id},${edge.label}") }
            }
            book.performedActions.forEach { action -> appendln("ACTION=${action.label},${action.source.id},${action.destination.id}") }
            return toString()
        }
    }


    fun load(filename: String): AdventureBook {
        val readLines = File(filename).readLines()
        return import(readLines)
    }


    fun import(importData: List<String>): AdventureBook {
        val bookEntryMap: Map<Int, BookEntry> = importBookEntries(importData)
        val title = importTitle(importData)
        val currentBookEntryId = importCurrentBookEntry(importData)
        val labeledEdges = importLabeledEdges(importData, bookEntryMap)
        val performedActions = importActions(importData, bookEntryMap)

        return AdventureBook(title, bookEntryMap, currentBookEntryId, labeledEdges, performedActions)
    }


    private fun importActions(importData: List<String>, bookEntryMap: Map<Int, BookEntry>): List<Action> {
        return importData //
                .filter { s -> s.startsWith("ACTION") }
                .map { s -> s.split(',') }
                .map { a ->
                    Action( //
                            a[0].substring("ACTION".length + 1), //
                            bookEntryMap.getOrDefault(a[1].toInt(), BookEntry(1)), //
                            bookEntryMap.getOrDefault(a[2].toInt(), BookEntry(1)))
                }
    }

    private fun importCurrentBookEntry(importData: List<String>): Int {
        return importData[1].substring("CURRENT_BOOK_ENTRY".length + 1).toInt()
    }

    private fun importLabeledEdges(importData: List<String>, bookEntryMap: Map<Int, BookEntry>): List<Action> {
        return importData //
                .filter { s -> s.startsWith("LABELED_EDGE") }
                .map { s -> s.split(',') }
                .map { a ->
                    Action(a[2], //
                            bookEntryMap.getOrDefault(a[0].substring("LABELED_EDGE".length + 1).toInt(), BookEntry(1)), //
                            bookEntryMap.getOrDefault(a[1].toInt(), BookEntry(1)))
                }
    }

    private fun importBookEntries(importData: List<String>): Map<Int, BookEntry> {
        val bookEntries: List<BookEntry> = importData //
                .filter { s -> s.startsWith("BOOK_ENTRY") }
                .map { s -> s.split(',') }
                .map { a -> BookEntry(a[0].substring("BOOK_ENTRY".length + 1).toInt(), a[1], Visit.valueOf(a[2]), a[3]) }
        return bookEntries.map { it.id to it }.toMap()
    }

    private fun importTitle(importData: List<String>): String {
        return importData[0].substring("TITLE".length + 1)
    }


}
