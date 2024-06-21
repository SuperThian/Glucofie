package com.capstone.glucofie.view.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.glucofie.data.user.UserModel
import com.capstone.glucofie.data.api.ApiService
import com.capstone.glucofie.data.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel( private val authRepository: AuthRepository) : ViewModel() {
    suspend fun updateUser(id: String, username: String, email: String, type_diabetes: String, gender:String , token: String) {
        withContext(Dispatchers.IO) {
            try {
                authRepository.updateUser(id, username = username, email= email, type_diabetes = type_diabetes, gender, token)
            } catch (e: Exception) {

                throw e
        }
        }
    }
    suspend fun fetchCurrUser(id: String, token:String) : UserModel?{
        return authRepository.getUser(id, token)
    }
 }

