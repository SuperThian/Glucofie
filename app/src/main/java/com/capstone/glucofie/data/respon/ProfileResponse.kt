package com.capstone.glucofie.data.respon

import com.capstone.glucofie.data.user.UpdateUserRequest
import com.google.gson.annotations.SerializedName

data class ProfileResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: UpdateUserRequest?

)
