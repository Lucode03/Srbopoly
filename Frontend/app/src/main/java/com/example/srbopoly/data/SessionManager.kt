package com.example.srbopoly.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager(private val context: Context) {

    companion object {
        val KEY_USER_ID = intPreferencesKey("user_id")
        val KEY_USERNAME = stringPreferencesKey("username")
        val KEY_POINTS = intPreferencesKey("points")
        val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveUser(id: Int, username: String, points: Int) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_ID] = id
            prefs[KEY_USERNAME] = username
            prefs[KEY_POINTS] = points
            prefs[KEY_IS_LOGGED_IN] = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val userFlow = context.dataStore.data.map { prefs ->
        if (prefs[KEY_IS_LOGGED_IN] == true) {
            UserSession(
                id = prefs[KEY_USER_ID] ?: 0,
                username = prefs[KEY_USERNAME] ?: "",
                points = prefs[KEY_POINTS] ?: 0
            )
        } else null
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}

data class UserSession(
    val id: Int,
    val username: String,
    val points: Int
)