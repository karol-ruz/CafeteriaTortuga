package com.example.myapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // USE "10.0.2.2" FOR ANDROID EMULATOR
    // USE YOUR LOCAL IP (e.g., "192.168.1.X") FOR PHYSICAL DEVICE
    private const val BASE_URL = "http://10.0.2.2:8081/"

    private val logging = okhttp3.logging.HttpLoggingInterceptor().apply {
        level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
    }

    private val client = okhttp3.OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productoApi: ProductoApi = retrofit.create(ProductoApi::class.java)
    val userApi: UserApi = retrofit.create(UserApi::class.java)
}
