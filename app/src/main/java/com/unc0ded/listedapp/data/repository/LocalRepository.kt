package com.unc0ded.listedapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val authTokenKey = stringPreferencesKey("auth_token")

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[authTokenKey] = authToken
        }
    }

    suspend fun getAuthToken(): String {
        return dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { preferences -> preferences[authTokenKey] ?: "" }
            .first()
    }
}