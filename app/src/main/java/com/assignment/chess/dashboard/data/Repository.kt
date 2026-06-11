package com.assignment.chess.dashboard.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.assignment.chess.dashboard.model.Position
import kotlinx.coroutines.flow.first


data class SavedSolution(
    val start: Position,
    val end: Position,
    val boardSize: Int,
    val maxMove: String,
    val paths: List<List<Position>>,
)

class Repository(private val context: Context) {
    suspend fun save(solution: SavedSolution) {
        context.dataStore.edit { prefs ->
            prefs[ChessPrefsKeys.START_ROW] = solution.start.row
            prefs[ChessPrefsKeys.START_COL] = solution.start.col
            prefs[ChessPrefsKeys.END_ROW] = solution.end.row
            prefs[ChessPrefsKeys.END_COL] = solution.end.col
            prefs[ChessPrefsKeys.BOARD_SIZE] = solution.boardSize
            prefs[ChessPrefsKeys.MAX_MOVE] = solution.maxMove
            prefs[ChessPrefsKeys.PATHS] = encodePaths(solution.paths)
        }

    }

    private fun encodePaths(paths: List<List<Position>>): String {
        return paths.joinToString("|") { path ->
            path.joinToString(",") { "${it.row}:${it.col}" }
        }
    }
    private fun decodePaths(raw: String): List<List<Position>> {
        if (raw.isEmpty()) return emptyList()
        return raw.split("|").map { segment ->
            segment.split(",").map { part ->
                val (r, c) = part.split(":")
                Position(r.toInt(), c.toInt())
            }
        }}

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun load(): SavedSolution? {
        val prefs = context.dataStore.data.first()
        val startRow = prefs[ChessPrefsKeys.START_ROW] ?: return null
        val startCol = prefs[ChessPrefsKeys.START_COL] ?: return null
        val endRow = prefs[ChessPrefsKeys.END_ROW] ?: return null
        val endCol = prefs[ChessPrefsKeys.END_COL] ?: return null
        val boardSize = prefs[ChessPrefsKeys.BOARD_SIZE] ?: return null
        val maxMove = prefs[ChessPrefsKeys.MAX_MOVE] ?: "3"
        val pathsJson = prefs[ChessPrefsKeys.PATHS] ?: ""
        return SavedSolution(
            start = Position(startRow, startCol),
            end = Position(endRow, endCol),
            boardSize = boardSize,
            maxMove = maxMove,
            paths = decodePaths(pathsJson),
        )
    }
}
