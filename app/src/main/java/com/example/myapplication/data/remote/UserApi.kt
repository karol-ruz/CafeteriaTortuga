package com.example.myapplication.data.remote

import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest)

    @POST("api/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest)
}
