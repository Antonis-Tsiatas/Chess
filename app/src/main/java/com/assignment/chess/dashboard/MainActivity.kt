package com.assignment.chess.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.assignment.chess.dashboard.view.Dashboard
import com.assignment.chess.dashboard.viewmodel.ChessboardViewmodel
import com.assignment.chess.ui.theme.ChessTheme

class MainActivity : ComponentActivity() {
    private val viewmodel: ChessboardViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChessTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Dashboard(
                        modifier = Modifier.padding(innerPadding),
                        chessboard = viewmodel.board,
                        markSquare = viewmodel::markSquare,
                        startPosition = viewmodel.startPosition,
                        endPosition = viewmodel.endPosition,
                        paths = viewmodel.paths,
                        isSearching = viewmodel.isSearching,
                        sizeText = viewmodel.sizeText,
                        onSizeChange = viewmodel::onSizeChange,
                        maxMove = viewmodel.maxMove,
                        onMaxMoveChange = viewmodel::onMaxMovesChange,
                        reset = viewmodel::reset,
                    )
                }
            }
        }
    }
}
