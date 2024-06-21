package com.capstone.glucofie.data.respon

import com.google.gson.annotations.SerializedName

data class ResultResponse(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("result")
    val result: Map<String, Any>, // Assuming result can be any type of data, use Map<String, Any>

    @field:SerializedName("suggestion")
    val suggestion: String,
)
