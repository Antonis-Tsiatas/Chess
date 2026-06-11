package com.assignment.chess.dashboard.domain

import com.assignment.chess.dashboard.model.Position

object KnightMoves {
    val moves = listOf(
        Position(-1,-2),
        Position(1,-2),
        Position(-2,-1),
        Position(2,-1),
        Position(-1,2),
        Position(1,2),
        Position(2,1),
        Position(-2,1)
    )
}