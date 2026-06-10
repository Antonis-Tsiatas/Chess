package com.assignment.chess.dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
) {
    val size = chessboard.size
    Board(
        modifier = modifier.padding(top = 40.dp),
        size,
        markSquare,
        startPosition,
        endPosition,
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(size),
        modifier = modifier
    ) {
        items(size * size) { index ->
            val row = index / size
            val col = index % size
            val position = Position(row,col)
            var color = when{
                    position == startPosition -> Color.Green
                    position == endPosition -> Color.Red
                    (row + col) % 2 == 1 -> Color.Black
                    else -> Color.White
            }
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(color)
                    .clickable { markSquare(position) },
                contentAlignment = Alignment.Center
            ) {

            }
        }
    }

}