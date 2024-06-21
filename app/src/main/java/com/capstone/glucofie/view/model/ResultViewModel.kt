package com.capstone.glucofie.view.model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.glucofie.data.user.UserModel
import com.capstone.glucofie.data.api.ApiService
import com.capstone.glucofie.data.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ResultViewModel( private val authRepository: AuthRepository) : ViewModel() {
    suspend fun getResultAnalyze(token: String) = authRepository.getResultAnalyze(token)
}

