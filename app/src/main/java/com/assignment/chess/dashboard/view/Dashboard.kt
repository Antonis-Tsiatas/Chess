package com.assignment.chess.dashboard.view

import androidx.compose.foundation.background
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

@Composable
fun Dashboard(chessboard: Chessboard, modifier: Modifier = Modifier){
    val size = chessboard.size
    Board(
        size,
        modifier=Modifier.padding(top = 40.dp)
    )
}

@Composable
fun Board(size: Int, modifier: Modifier){
    LazyVerticalGrid(
        columns = GridCells.Fixed(size),
        modifier = modifier
    ) {
        items(size*size) { index ->
            val row = index/ size
            val col = index% size
            var color = Color.White
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(if ((row + col) %2 == 1) Color.Black else color),
                contentAlignment = Alignment.Center
            ) {

            }
        }
    }

}