package com.d20charactersheet.adventurebookresolver.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test

class CombinationsTest {

    @Test
    fun `should have zero combinations when list of way points is empty`() {

        // Act
        val combinations = Combinations(listOf())

        // Assert
        assertThat(combinations.isEmpty()).isTrue
    }

    @Test
    fun `should have one combination when list of way points has one way point`() {
        // Arrange
        val wayPoints: List<BookEntry> = listOf(BookEntry(1))

        // Act
        val combinations = Combinations(wayPoints)

        // Assert
        assertThat(combinations.combinations).contains(Combination(arrayOf(BookEntry(1))))
    }

    @Test
    fun `should have two combinations when list of way points has two waypoints`() {
        // Arrange
        val wayPoints: List<BookEntry> = listOf(BookEntry(1), BookEntry(2))

        // Act
        val combinations = Combinations(wayPoints)

        // Assert
        assertThat(combinations.combinations).containsExactlyInAnyOrder(//
            Combination(arrayOf(BookEntry(1), BookEntry(2))),
            Combination(arrayOf(BookEntry(2), BookEntry(1)))
        )
    }

    @Test
    fun `should have six combinations when list of way points contains three way points`() {
        // Arrange
        val wayPoints: List<BookEntry> = listOf(BookEntry(1), BookEntry(2), BookEntry(3))

        // Act
        val combinations = Combinations(wayPoints)

        // Assert
        assertThat(combinations.combinations).containsExactlyInAnyOrder(//
            Combination(arrayOf(BookEntry(1), BookEntry(2), BookEntry(3))),
            Combination(arrayOf(BookEntry(1), BookEntry(3), BookEntry(2))),
            Combination(arrayOf(BookEntry(2), BookEntry(3), BookEntry(1))),
            Combination(arrayOf(BookEntry(2), BookEntry(1), BookEntry(3))),
            Combination(arrayOf(BookEntry(3), BookEntry(1), BookEntry(2))),
            Combination(arrayOf(BookEntry(3), BookEntry(2), BookEntry(1)))
        )
    }

    @Test
    fun `should throw IllegalArgumentException when list of way points contains more than ten way points`() {
        // Arrange
        val wayPoints: List<BookEntry> = listOf( //
            BookEntry(1), BookEntry(2), BookEntry(3), BookEntry(4), BookEntry(5), BookEntry(6), //
            BookEntry(7), BookEntry(8), BookEntry(9), BookEntry(10), BookEntry(11)
        )

        // Act
        val throwable: Throwable =
            catchThrowable { Combinations(wayPoints) }

        // Assert
        assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("too many way points, calculation would take to long")
    }

    @Test
    internal fun `should remove connections when missing connection is found`() {
        // arrange
        val combinations = Combinations( //
            listOf(
                BookEntry(1, "one"),
                BookEntry(2, "two"),
                BookEntry(3, "three")
            )
        )

        // act
        combinations.remove(Connection(BookEntry(3), BookEntry(2)))

        // assert
        assertThat(combinations.size()).isEqualTo(4)
    }

    @Test
    internal fun `should return true for hasNext when more combinations exist`() {

        // act
        val hasNext = Combinations(listOf(BookEntry(1))).hasNextCombination()

        // assert
        assertThat(hasNext).isTrue
    }

    @Test
    internal fun `should return false for hasNext when no more combinations exist`() {
        // act
        val hasNext = Combinations(emptyList()).hasNextCombination()

        // assert
        assertThat(hasNext).isFalse
    }

    @Test
    internal fun `should return next combination when more combinations exist`() {

        // arrange
        val bookEntry = BookEntry(1)

        // act
        val combination = Combinations(listOf(bookEntry)).next()

        // assert
        assertThat(combination.wayPoints).containsExactly(bookEntry)
    }

    @Test
    internal fun `should throw IllegalStateException when no more combinations exist`() {

        // act
        val throwable = catchThrowable { Combinations(emptyList()).next() }

        // assert
        assertThat(throwable).isInstanceOf(IllegalStateException::class.java)
            .hasMessage("No more combinations available")

    }

    @Test
    internal fun `should remove given combination`() {
        // arrange
        val combinations = Combinations(listOf(BookEntry(1), BookEntry(2)))

        // act
        combinations.remove(combinations.combinations[0])

        // assert
        assertThat(combinations.size()).isEqualTo(1)
    }

}