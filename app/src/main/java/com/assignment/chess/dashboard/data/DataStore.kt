package com.assignment.chess.dashboard.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "chess_prefs")

object ChessPrefsKeys {
    val START_ROW = intPreferencesKey("start_row")
    val START_COL = intPreferencesKey("start_col")
    val END_ROW = intPreferencesKey("end_row")
    val END_COL = intPreferencesKey("end_col")
    val BOARD_SIZE = intPreferencesKey("board_size")
    val MAX_MOVE = stringPreferencesKey("max_move")
    val PATHS = stringPreferencesKey("paths")
}