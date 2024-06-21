package com.capstone.glucofie.data.api.inject

import android.content.Context
import com.capstone.glucofie.data.AuthRepository
import com.capstone.glucofie.data.api.ApiConfig
import com.capstone.glucofie.data.user.UserPreference
import com.capstone.glucofie.data.user.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): AuthRepository = runBlocking {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = pref.getSession().first()
        val apiService = ApiConfig.getApiService(user.token)
        AuthRepository.getInstance(apiService, pref)
    }
}
