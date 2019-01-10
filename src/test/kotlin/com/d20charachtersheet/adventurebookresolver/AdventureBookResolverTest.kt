package com.d20charachtersheet.adventurebookresolver

import com.d20charachtersheet.adventurebookresolver.domain.BookEdge
import com.d20charachtersheet.adventurebookresolver.domain.BookEntry
import com.d20charachtersheet.adventurebookresolver.domain.Visit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AdventureBookResolverTest {

    @Nested
    inner class StartupTest {

        @Test
        internal fun `start new adventure at vertex 1`() {
            // Act
            val underTest = AdventureBookResolver("Der Forst der Finsternis")

            // Assert
            assertThat(underTest.title).isEqualTo("Der Forst der Finsternis")
            assertThat(underTest.dumpGraph()).isEqualTo("([BookEntry(id=1)], [])")
            assertThat(underTest.getEntryId()).isEqualTo(1)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

    }

    @Nested
    inner class CommandTest {

        private var underTest = AdventureBookResolver("Der Forst der Finsternis")

        @Test
        internal fun `edit title of current book entry (command edit)`() {
            // Act
            underTest.editBookEntry("Yaztronmos Behausung")

            // Assert
            assertThat(underTest.getEntryTitle()).isEqualTo("Yaztronmos Behausung")
        }

        @Test
        internal fun `add new book entry (command add)`() {
            // Act
            underTest.addBookEntry(261, "nach oben")

            // Assert
            assertThat(underTest.dumpGraph()).isEqualTo("([BookEntry(id=1), BookEntry(id=261)], [(BookEntry(id=1) : BookEntry(id=261))=(BookEntry(id=1),BookEntry(id=261))])")
            assertThat(underTest.getEdges()).extracting("label").containsExactly("nach oben")
            assertThat(underTest.getBookEntries()).containsExactly(BookEntry(261)).extracting("visit").containsExactly(Visit.UNVISITED)
        }

        @Test
        internal fun `move to other book entry (command move)`() {
            // Arrange
            underTest.addBookEntry(261, "nach oben")

            // Act
            underTest.moveToBookEntry(261)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(261)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }

        @Test
        internal fun `move only possible to existing entry`() {
            // Arrange
            underTest.addBookEntry(261, "nach oben")

            // Act
            underTest.moveToBookEntry(1000)

            // Assert
            assertThat(underTest.getEntryId()).isEqualTo(1)
            assertThat(underTest.getEntryVisit()).isEqualTo(Visit.VISITED)
        }
    }

    @Nested
    inner class InformationTest {

        private var underTest = AdventureBookResolver("Der Forst der Finsternis")

        @BeforeEach
        internal fun setup() {
            underTest.addBookEntry(261, "nach oben")
            underTest.addBookEntry(54, "Schwert ziehen")
        }

        @Test
        internal fun `get list of existing edges of current book entry`() {
            // Act
            val edges: Set<BookEdge> = underTest.getEdges()

            // Assert
            assertThat(edges).extracting("label").containsExactlyInAnyOrder("nach oben", "Schwert ziehen")
        }

        @Test
        internal fun `get list of book entries to move to`() {
            // Act
            val bookEntries: Set<BookEntry> = underTest.getBookEntries()

            // Assert
            assertThat(bookEntries).extracting("title").containsExactly("Untitled", "Untitled")
        }

        @Test
        internal fun `get dump of current graph`() {
            // Act
            val dumpOfGraph = underTest.dumpGraph()

            // Assert
            assertThat(dumpOfGraph).isEqualTo("([BookEntry(id=1), BookEntry(id=261), BookEntry(id=54)], [(BookEntry(id=1) : BookEntry(id=261))=(BookEntry(id=1),BookEntry(id=261)), (BookEntry(id=1) : BookEntry(id=54))=(BookEntry(id=1),BookEntry(id=54))])")
        }

    }


}