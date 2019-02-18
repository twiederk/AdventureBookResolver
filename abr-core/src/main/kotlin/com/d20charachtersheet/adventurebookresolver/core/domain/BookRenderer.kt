package com.d20charachtersheet.adventurebookresolver.core.domain

import com.mxgraph.layout.mxCompactTreeLayout
import com.mxgraph.util.mxCellRenderer
import org.jgrapht.ext.JGraphXAdapter
import java.awt.Color
import java.nio.file.Path
import java.nio.file.Paths
import javax.imageio.ImageIO

class BookRenderer {

    fun renderGraph(book: AdventureBook): Path {
        val graphAdapter = renderGraphInMemory(book)
        return writeImageToFile(graphAdapter)
    }

    private fun renderGraphInMemory(book: AdventureBook): JGraphXAdapter<BookEntry, LabeledEdge> {
        val graphAdapter = JGraphXAdapter<BookEntry, LabeledEdge>(book.graph)
        val unvisitedCells = book.graph.vertexSet().filter { it.visit == Visit.UNVISITED }.map { graphAdapter.vertexToCellMap[it] }.toList()
        graphAdapter.setSelectionCells(unvisitedCells)
        graphAdapter.setCellStyle("defaultVertex;fillColor=yellow")
        val layout = mxCompactTreeLayout(graphAdapter, false)
        layout.execute(graphAdapter.defaultParent)
        return graphAdapter
    }

    private fun writeImageToFile(graphAdapter: JGraphXAdapter<BookEntry, LabeledEdge>): Path {
        val image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2.0, Color.WHITE, true, null)
        val imagePath = Paths.get("graph.png")
        ImageIO.write(image, "PNG", imagePath.toFile())
        return imagePath
    }

}
