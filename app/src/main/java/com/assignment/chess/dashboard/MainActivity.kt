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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                        uiState = viewmodel.uiState,
                        markSquare = viewmodel::markSquare,
                        onSizeChange = viewmodel::onSizeChange,
                        onMaxMoveChange = viewmodel::onMaxMovesChange,
                        reset = viewmodel::reset,
                    )
                }
            }
        }
    }
}
