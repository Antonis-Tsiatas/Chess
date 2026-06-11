package com.assignment.chess.dashboard.domain

import com.assignment.chess.dashboard.model.Position

object Dfs {

    fun dfs(
        moves: List<Position>,
        path: MutableList<Position>,
        paths: MutableList<List<Position>>,
        position: Position,
        target: Position
    ) {
        if (position == target) {
            paths.add(path.toList())
            return
        }
        if (path.size == 3) {
            return
        }
        for (move in moves) {
            val newPosition = sum(move, position)
            if (newPosition.row >= 0 && newPosition.col >= 0 && newPosition.col < 6 && newPosition.row < 6)
                continue
            path.add(newPosition)
            dfs(moves, path, paths, newPosition, target)
            path.removeAt(path.lastIndex)
        }
    }

    private fun sum(
        move: Position,
        position: Position
    ): Position {
        val row = position.row + move.row
        val col = position.col + move.col
        return Position(row, col)
    }

}