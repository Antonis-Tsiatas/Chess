package com.assignment.chess.dashboard.domain

import com.assignment.chess.dashboard.model.Position

object Dfs {

    fun dfs(
        moves: List<Position>,
        path: MutableList<Position>,
        paths: MutableList<List<Position>>,
        visited: MutableSet<Position>,
        position: Position,
        target: Position,
        maxMove: Int,
        boardSize: Int,
    ) {
        if (position == target) {
            paths.add(path.toList())
            return
        }
        if (path.size == maxMove) {
            return
        }
        for (move in moves) {
            val newPosition = sum(move, position)
            if (newPosition in visited) continue
            if (newPosition.row !in 0 until boardSize || newPosition.col !in 0 until boardSize) continue
            path.add(newPosition)
            visited.add(newPosition)
            dfs(moves, path, paths, visited, newPosition, target, maxMove, boardSize)
            path.removeAt(path.lastIndex)
            visited.remove(newPosition)
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
