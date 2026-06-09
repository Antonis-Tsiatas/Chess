package com.assignment.chess.dashboard.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.assignment.chess.dashboard.model.Chessboard
import com.assignment.chess.dashboard.model.Position
import com.assignment.chess.dashboard.model.Square

class ChessboardViewmodel : ViewModel(){

    var board by mutableStateOf(createBoard(6))
        private set

    private fun createBoard(size: Int): Chessboard{
        val squares = List(size*size){
            index ->
            val row = index/size
            val col = index%size

            Square(Position(row,col),false)
        }
        return Chessboard(size,squares)
    }
}