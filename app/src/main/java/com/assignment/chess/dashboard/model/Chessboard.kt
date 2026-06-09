package com.assignment.chess.dashboard.model

data class Chessboard(
    val size: Int,
    val squares: List<Square>
)

data class Square(
    val position: Position,
    val horse: Boolean?=false
)

data class Position(
    val row: Int,
    val col: Int,
)