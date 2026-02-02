package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val username: String,
    @SerializedName("password")
    val password: String
)
