package com.example.task1

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class CoroutineNetwork {
}

private val service: DummyNetwork by lazy {
    val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor(DummyNetworkInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://jsonplaceholder.typicode.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(DummyNetwork::class.java)
}

fun getNetworkService() = service

/**
 * Main network interface which will fetch a new welcome title for us
 */
interface DummyNetwork {
    @GET("users")
//    suspend fun fetchUsers(): List<UserResponseItem>
    suspend fun fetchUsers(): UserResponseItem

    @GET("users/{user_id}")
    suspend fun fetchUserById(@Path("user_id")user_id: String): UserResponseItem
}