package com.capstone.glucofie.data

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.glucofie.data.api.ApiService
import com.capstone.glucofie.data.api.LoginRequest
import com.capstone.glucofie.data.api.RegisterRequest
import com.capstone.glucofie.data.respon.LoginResponse
import com.capstone.glucofie.data.respon.RegisterResponse
import com.capstone.glucofie.data.respon.ResultResponse
import com.capstone.glucofie.data.respon.ScanResponse
import com.capstone.glucofie.data.user.UpdateUserRequest
import com.capstone.glucofie.data.user.UserModel
import com.capstone.glucofie.data.user.UserPreference
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import kotlin.reflect.typeOf

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        if (password != confirmPassword) {
            emit(Result.Error("Passwords do not match"))
            return@liveData
        }
        try {
            val request = RegisterRequest(username, email, password, confirmPassword)
            val response = apiService.register(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (e.code() == 400 && errorBody != null && errorBody.contains("Email is already taken")) {
                emit(Result.Error("Email is already taken"))
            } else {
                emit(Result.Error("HTTP error: ${e.message}"))
            }
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected error: ${e.message}"))
        }
    }
    private fun createJsonRequestBodyFile(vararg params: Pair<String, MultipartBody.Part>) =
        RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(mapOf(*params)).toString())
    private fun createJsonRequestBody(vararg params: Pair<String, String>) =
        RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(mapOf(*params)).toString())
    suspend fun updateUser(id: String,username: String, email: String, type_diabetes: String, gender:String , token: String) {
        try {
            val bearerToken = "Bearer ${token}"
            val response = apiService.updateUserById(id, createJsonRequestBody(
                "id" to id, "username" to username, "email" to email, "gender" to gender, "type_diabetes" to type_diabetes
            ), bearerToken)

        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun getResultAnalyze(token:String): ResultResponse {
        Log.d("Token", token)
        try {
            val bearerToken = "Bearer $token"
            val response = apiService.getResult(bearerToken)

            return response.get(response.size - 1)
        } catch (e: HttpException) {
            Log.e("ResultAnalyze", "HttpException: ${e.message()}")
            throw e
        } catch (e: Exception) {
            Log.e("ResultAnalyze", "Exception: ${e.message}")
            throw e
        }

    }

     suspend fun sendImage(file: File, token: String ) {
        try {
            Log.e("zxczx", "sendImage: ", )
            val response = apiService.detectNutrition(

                image = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    file.asRequestBody("image/png".toMediaTypeOrNull())
                )
            , token = "Bearer $token")

            Log.e("FileResponse","sendImage: ${response.message}", )

        }catch(e : HttpException){
            Log.e("xcz", "HttpExceptionImage: ${e.toString()}", )
            throw e
        }catch(e : Exception){
            Log.e("xcz", "ExceptionImage: ${e.toString()}", )
            throw e
        }
    }
    suspend fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error("HTTP error: ${e.message}"))
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected error: ${e.message}"))
        }
    }
    suspend fun getUser(id: String, token: String): UserModel? {
        return try {
            apiService.getUserById(id, token)[0]
        } catch (e: Exception) {
            null // or you could rethrow the exception or handle it as per your requirements
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun clearInstance() {
            instance = null
        }

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, userPreference)
            }.also { instance = it }
    }
}