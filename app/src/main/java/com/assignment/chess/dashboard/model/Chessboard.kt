package com.assignment.chess.dashboard.model

data class Chessboard(
    val size: Int,
    val squares: List<Square>
)

data class Square(
    val position: Position
)

data class Position(
    val row: Int,
    val col: Int,
)
fun Position.toSquare(size: Int) = "${'a' + col}${size - row}"
