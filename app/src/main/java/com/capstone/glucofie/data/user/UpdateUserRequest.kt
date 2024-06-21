package com.capstone.glucofie.data.user

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("id") val userId: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("type_diabetes") val typeDiabetes: String?,
)
