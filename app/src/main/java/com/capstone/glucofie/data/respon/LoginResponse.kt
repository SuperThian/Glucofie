package com.capstone.glucofie.data.respon

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("loginResult")
    val loginResult: LoginResult? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class LoginResult(

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("type_diabetes")
    val typeDiabetes: String,
    @field:SerializedName("gender")
    val gender: String
)