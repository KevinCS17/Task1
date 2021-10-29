package com.example.task1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.google.android.play.core.internal.t
import kotlinx.coroutines.*
import retrofit2.HttpException

class CoroutineRepository(val network: DummyNetwork) {

    val data = MutableLiveData<List<UserResponseItem>>()

    suspend fun fetchUsersById(stringNumber : String) {
        try {
            val result = withTimeout(5000) {
                network.fetchUserById(stringNumber)
            }
            data.postValue(listOf(result))
        } catch (error: Throwable) {
            Log.d("testinghaha","${error.message}")
        }
    }
}

class TitleRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

interface UserResponseCallback{
    fun onSuccess():MutableLiveData<List<UserResponseItem>>
    fun onError(t:Throwable)
}