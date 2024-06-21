package com.capstone.glucofie.view.model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
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

class ScanViewModel( private val authRepository: AuthRepository) : ViewModel() {

    fun sendImage(file: File, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
                authRepository.sendImage(file, token)

            } catch (e: Exception) {
            }
        }
    }
}

