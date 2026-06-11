package com.assignment.chess.dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.assignment.chess.dashboard.model.Chessboard
import com.assignment.chess.dashboard.model.Position

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    chessboard: Chessboard,
    markSquare: (Position?) -> Unit,
    startPosition: Position?,
    endPosition: Position?,
    paths: List<List<Position>>,
    isSearching: Boolean,
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 16.dp)
    ) {
        item {
            val size = chessboard.size
            Board(
                modifier = modifier.padding(top = 40.dp),
                size,
                markSquare,
                startPosition,
                endPosition,
            )
        }
        item {
            PathsList(paths, isSearching)
        }
    }
}

@Composable
fun Board(
    modifier: Modifier,
    size: Int,
    markSquare: (Position?) -> Unit,
    startPosition: Position?,
    endPosition: Position?,
) {
    for (row in 0 until size) {
        Row {
            for (col in 0 until size) {
                val position = Position(row, col)
                val color = when {
                    position == startPosition -> Color.Green
                    position == endPosition -> Color.Red
                    (row + col) % 2 == 1 -> Color.Black
                    else -> Color.White
                }
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(color)
                        .clickable { markSquare(position) }
                )
            }
        }
    }

}

@Composable
fun PathsList(
    paths: List<List<Position>>,
    isSearching: Boolean
) {
    Column(modifier = Modifier.padding(16.dp)) {
        when {
            isSearching -> {
                Text("Searching")
            }

            paths.isEmpty() -> Text("no solution", color = Color.Red)
            else -> {
                paths.forEach { path ->
                    Text(path.toString())
                }
            }
        }

    }
}