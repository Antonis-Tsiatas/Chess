package com.assignment.chess.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.chess.dashboard.data.Repository

class ChessViewModelFactory(
    private val repository: Repository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChessboardViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChessboardViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}