package com.capstone.glucofie.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.glucofie.data.AuthRepository
import com.capstone.glucofie.data.Result
import com.capstone.glucofie.data.respon.RegisterResponse

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    suspend fun register(username: String, email: String, password: String, confirmPassword: String): LiveData<Result<RegisterResponse>> {
        return authRepository.register(username, email, password, confirmPassword)
    }
}
