package com.capstone.glucofie.data.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USER_ID].toString(),
                preferences[NAME_KEY].toString(),
                preferences[EMAIL_KEY].toString(),
                preferences[TYPE_DIABETES_KEY].toString(),
                preferences[GENDER_KEY].toString(),
                preferences[TOKEN_KEY].toString(),
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token
            preferences[NAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[TYPE_DIABETES_KEY] = user.type_diabetes
            preferences[GENDER_KEY] = user.gender
            preferences[USER_ID] = user.id
            preferences[IS_LOGIN_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = ""
            preferences[NAME_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[TYPE_DIABETES_KEY] = ""
            preferences[GENDER_KEY] = ""
            preferences[USER_ID] = ""
            preferences[IS_LOGIN_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("username")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TYPE_DIABETES_KEY = stringPreferencesKey("type_diabetes")
        private val GENDER_KEY = stringPreferencesKey("gender")
        private val USER_ID = stringPreferencesKey("id")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
