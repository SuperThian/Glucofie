package com.capstone.glucofie.view.model

import androidx.lifecycle.ViewModel
import com.capstone.glucofie.data.AuthRepository
import com.capstone.glucofie.data.user.UserModel

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    suspend fun login(email: String, password: String) = authRepository.login(email, password)
    suspend fun fetchCurrUser(id: String, token:String) : UserModel?{
        return authRepository.getUser(id, token)
    }
    suspend fun saveSession(user: UserModel) {
        authRepository.saveSession(user)
    }
}
