package com.assignment.chess.dashboard.domain

import com.assignment.chess.dashboard.model.Position
import org.junit.Test

class DfsTest{

    @Test
    fun dfsFindsPathOn8x8Board(){
        val start = Position(0, 0)
        val end = Position(2, 1)
        val paths = mutableListOf<List<Position>>()
        val visited = mutableSetOf(start)

        Dfs.dfs(
            KnightMoves.moves,
            mutableListOf(),
            paths,
            visited,
            start,
            end,
            maxMove = 3,
            boardSize =  8
        )

    }
}