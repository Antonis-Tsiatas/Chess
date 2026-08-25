package com.assignment.chess.dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.assignment.chess.dashboard.model.Position
import com.assignment.chess.dashboard.model.toSquare

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    uiState: ChessUiState,
    markSquare: (Position?) -> Unit,
    onSizeChange: (String) -> Unit,
    onMaxMoveChange: (String) -> Unit,
    reset: () -> Unit,
) {
    val boardSize = uiState.board.size

    LazyColumn(
        modifier = modifier
            .padding(top = 16.dp)
            .imePadding()
    ) {
        item {
            ButtonAndTitle("board size", uiState.sizeText, onSizeChange, uiState.sizeError)
        }
        item {
            ButtonAndTitle("max movements", uiState.maxMove, onMaxMoveChange)
        }
        item {
            Button(
                onClick = reset,
                modifier = Modifier.padding(16.dp),
            ) {
                Text("reset")
            }
        }
        item {
            Board(
                modifier = modifier.padding(top = 16.dp),
                boardSize,
                markSquare,
                uiState.startPosition,
                uiState.endPosition,
            )
        }

        when {
            uiState.isSearching -> {
                item {
                    Text(
                        text = "Searching",
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }

            uiState.startPosition == null -> {
                item {
                    Text(
                        text = "tap start position",
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }

            uiState.endPosition == null -> {
                item {
                    Text(
                        text = "tap end position",
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }

            uiState.paths.isEmpty() -> {
                item {
                    Text(
                        text = "no solution",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }

            else -> {
                val start = uiState.startPosition ?: return@LazyColumn
                items(
                    items = uiState.paths,
                    key = { path ->
                        path.joinToString("|") { "${it.row}:${it.col}" }
                    },
                ) { path ->
                    Text(
                        text = (listOf(start) + path)
                            .joinToString(" → ") { it.toSquare(boardSize) },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonAndTitle(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
) {
    Text(title, modifier = Modifier.padding(start = 16.dp, top = 8.dp))
    TextField(
        value = value,
        onValueChange = onValueChange,
        isError = error != null,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier.padding(horizontal = 16.dp),
        supportingText = error?.let { { Text(it) } },
    )
}

@Composable
fun Board(
    modifier: Modifier,
    size: Int,
    markSquare: (Position?) -> Unit,
    startPosition: Position?,
    endPosition: Position?,
) {
    Column(modifier = modifier) {
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
}
