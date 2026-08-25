package com.assignment.chess.dashboard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.chess.dashboard.data.Repository
import com.assignment.chess.dashboard.data.SavedSolution
import com.assignment.chess.dashboard.domain.Dfs.dfs
import com.assignment.chess.dashboard.domain.KnightMoves
import com.assignment.chess.dashboard.model.Chessboard
import com.assignment.chess.dashboard.model.Position
import com.assignment.chess.dashboard.model.Square
import com.assignment.chess.dashboard.view.ChessUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChessboardViewmodel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    var uiState by mutableStateOf(ChessUiState())
        private set

    init {
        viewModelScope.launch {
            repository.load()?.let { saved ->
                uiState = uiState.copy(
                    board = createBoard(saved.boardSize),
                    sizeText = saved.boardSize.toString(),
                    maxMove = saved.maxMove,
                    startPosition = saved.start,
                    endPosition = saved.end,
                    paths = saved.paths,
                )
            }
        }
    }

    private fun createBoard(size: Int): Chessboard {
        val squares = List(size * size) { index ->
            val row = index / size
            val col = index % size
            Square(Position(row, col))
        }
        return Chessboard(size, squares)
    }

    fun markSquare(position: Position?) {
        val current = uiState
        val updated = when {
            current.startPosition == null -> current.copy(startPosition = position)
            current.endPosition == null -> current.copy(endPosition = position)
            else -> current.copy(startPosition = position, endPosition = null, paths = emptyList())
        }
        uiState = updated

        val start = updated.startPosition
        val end = updated.endPosition
        if (start != null && end != null) {
            viewModelScope.launch {
                uiState = uiState.copy(isSearching = true)
                val results = withContext(Dispatchers.Default) {
                    findPath(start, end)
                }
                uiState = uiState.copy(
                    paths = results,
                    isSearching = false,
                )
                repository.save(
                    SavedSolution(
                        start = start,
                        end = end,
                        boardSize = uiState.board.size,
                        maxMove = uiState.maxMove,
                        paths = results,
                    )
                )
            }
        }
    }

    fun findPath(start: Position, end: Position): List<List<Position>> {
        val moves = KnightMoves.moves.map { (row, col) -> Position(row, col) }
        val currentPath = mutableListOf<Position>()
        val resultPaths = mutableListOf<List<Position>>()
        val visited = mutableSetOf(start)
        val maxMoves = uiState.maxMove.toIntOrNull() ?: 3
        dfs(moves, currentPath, resultPaths, visited, start, end, maxMoves, uiState.board.size)
        return resultPaths
    }

    fun reset() {
        uiState = uiState.copy(
            startPosition = null,
            endPosition = null,
            paths = emptyList(),
            isSearching = false,
        )
        viewModelScope.launch {
            repository.clear()
        }
    }

    fun onSizeChange(value: String) {
        val digits = value.filter { it.isDigit() }
        if (digits.isEmpty()) {
            uiState = uiState.copy(sizeText = "", sizeError = null)
            return
        }
        val newSize = digits.toIntOrNull() ?: return

        when {
            newSize < 6 -> {
                uiState = uiState.copy(
                    sizeText = digits,
                    sizeError = "Minimum board size is 6",
                )
            }
            newSize > 16 -> {
                uiState = uiState.copy(
                    sizeText = digits,
                    sizeError = "Maximum board size is 16",
                )
            }
            else -> {
                uiState = uiState.copy(
                    sizeText = digits,
                    sizeError = null,
                    board = createBoard(newSize),
                )
                reset()
            }
        }
    }

    fun onMaxMovesChange(value: String) {
        val digits = value.filter { it.isDigit() }
        if (digits.isEmpty()) {
            uiState = uiState.copy(maxMove = "")
            return
        }
        val number = digits.toIntOrNull() ?: return
        if (number in 1..10) {
            uiState = uiState.copy(maxMove = digits)
        }
    }
}
