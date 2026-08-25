package com.assignment.chess.dashboard.view

import com.assignment.chess.dashboard.model.Chessboard
import com.assignment.chess.dashboard.model.Position
import com.assignment.chess.dashboard.model.Square

data class ChessUiState(
    val board: Chessboard = createDefaultBoard(),
    val startPosition: Position? = null,
    val endPosition: Position? = null,
    val paths: List<List<Position>> = emptyList(),
    val isSearching: Boolean = false,
    val sizeText: String = "6",
    val sizeError: String? = null,
    val maxMove: String = "3",
)

private fun createDefaultBoard(size: Int = 6): Chessboard {
    val squares = List(size * size) { index ->
        val row = index / size
        val col = index % size
        Square(Position(row, col))
    }
    return Chessboard(size, squares)
}
