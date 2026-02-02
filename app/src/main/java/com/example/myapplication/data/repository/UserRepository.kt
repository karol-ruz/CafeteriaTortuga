package com.example.myapplication.data.repository

import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.RegisterRequest
import com.example.myapplication.data.remote.UserApi

class UserRepository(private val api: UserApi) {
    suspend fun login(username: String, password: String) = api.login(LoginRequest(username, password))
    suspend fun register(username: String, password: String) = api.register(RegisterRequest(username, password))
}
