package com.capstone.glucofie.data.api

import com.capstone.glucofie.data.respon.LoginResponse
import com.capstone.glucofie.data.respon.ProfileResponse
import com.capstone.glucofie.data.respon.RegisterResponse
import com.capstone.glucofie.data.respon.ResultResponse
import com.capstone.glucofie.data.respon.ScanResponse
import com.capstone.glucofie.data.user.UpdateUserRequest
import com.capstone.glucofie.data.user.UserModel
import com.capstone.glucofie.view.model.ProfileViewModel
import com.google.firebase.firestore.auth.User
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

data class RegisterRequest(val username: String, val email: String, val password: String, val confirmPassword: String)
data class LoginRequest(val email: String, val password: String)

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: String,
        @Header("Authorization") token: String,

    ): List<UserModel>

    @GET("users")
    suspend fun getAllUsers(): List<UserModel>

    @GET("result")
    suspend fun getResult(
        @Header("Authorization") token: String,
    ): List<ResultResponse>

    @PATCH("users/{id}")
    suspend fun updateUserById(
        @Path("id") id: String,
        @Body request: RequestBody,
        @Header("Authorization") token: String,
        @Header("Content-Type") contentType: String = "application/json"
    ): ProfileResponse

    @Multipart
    @POST("detect-nutrition")
     suspend fun detectNutrition(
        @Part image: MultipartBody.Part,
        @Header("Authorization") token: String,
    ): ScanResponse

}