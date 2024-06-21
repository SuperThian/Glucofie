package com.capstone.glucofie.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.glucofie.data.user.UserModel
import com.capstone.glucofie.data.AuthRepository

class MainViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> = authRepository.getSession().asLiveData()

    suspend fun logout() {
        authRepository.logout()
    }
}
