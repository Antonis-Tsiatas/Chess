package com.assignment.chess.dashboard.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.chess.dashboard.domain.Dfs.dfs
import com.assignment.chess.dashboard.domain.KnightMoves
import com.assignment.chess.dashboard.model.Chessboard
import com.assignment.chess.dashboard.model.Position
import com.assignment.chess.dashboard.model.Square
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChessboardViewmodel : ViewModel() {

    var startPosition by mutableStateOf<Position?>(null)
        private set

    var endPosition by mutableStateOf<Position?>(null)
        private set

    var paths by mutableStateOf<List<List<Position>>>(emptyList())
        private set

    var maxMove by mutableStateOf("3")
        private set

    var sizeText by mutableStateOf("6")
        private set

    var size by mutableStateOf(6)
        private set

    var board by mutableStateOf(createBoard(6))
        private set

    var isSearching by mutableStateOf(false)
        private set

    private fun createBoard(size: Int): Chessboard {
        val squares = List(size * size) { index ->
            val row = index / size
            val col = index % size
            Square(Position(row, col))
        }
        return Chessboard(size, squares)
    }

    fun markSquare(position: Position?) {
        when {
            startPosition == null -> startPosition = position
            endPosition == null -> endPosition = position
            else -> {
                startPosition = position
                endPosition = null
            }
        }
        Log.d("myPosition start", startPosition.toString())
        Log.d("myPosition end", endPosition.toString())
        val start = startPosition
        val end = endPosition
        if (start != null && end != null)
            viewModelScope.launch {
                isSearching = true
                val results = withContext(Dispatchers.Default) {
                    findPath(start, end)
                }
                Log.d("findPath", "done, count=${results.size}")
                paths = results
                isSearching = false
            }
    }

    fun findPath(start: Position, end: Position): List<List<Position>> {
        val moves = KnightMoves.moves.map { (row, col) -> Position(row, col) }
        val currentPath = mutableListOf<Position>()
        val resultPaths = mutableListOf<List<Position>>()
        val visited = mutableSetOf(start)
        val maxMoves = maxMove.toIntOrNull() ?: 3
        dfs(moves, currentPath, resultPaths, visited, start, end, maxMoves, board.size)
        return resultPaths
    }

    fun reset() {
        startPosition = null
        endPosition = null
        paths = emptyList()
        isSearching = false
    }

    fun onSizeChange(value: String) {
        val digits = value.filter { it.isDigit() }
        sizeText = digits
        if (digits.isEmpty()) return
        val newSize = digits.toIntOrNull() ?: return
        if (newSize in 6..16) {
            size = newSize
            board = createBoard(newSize)
            reset()
        }
    }

    fun onMaxMovesChange(value: String) {
        val digits = value.filter { it.isDigit() }
        if (digits.isEmpty()) {
            maxMove = ""
            return
        }
        val number = digits.toIntOrNull() ?: return
        if (number in 1..10) {
            maxMove = digits
        }
    }
}
