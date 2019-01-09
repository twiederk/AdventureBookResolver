package com.d20charachtersheet.adventurebookresolver.domain

import org.jgrapht.graph.DefaultEdge

data class BookEdge(val label: String = "unlabeled") : DefaultEdge()

