package com.capstone.glucofie.data.user

data class UserModel(
    val id: String,
    val username: String,
    val email: String,
    val type_diabetes: String,
    val gender: String,
    val token: String,
    val isLogin: Boolean = false
)