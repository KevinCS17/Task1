package com.example.task1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.google.android.play.core.internal.t
import kotlinx.coroutines.*
import retrofit2.HttpException

class CoroutineRepository(val network: DummyNetwork) {

    val user = MutableLiveData<List<UserResponseItem>>()

    //    suspend fun fetchUsers():List<UserResponseItem>{
//        return withContext(Dispatchers.IO){
//            network.fetchUsers()
//        }
//    }
    suspend fun fetchUsers() {
        try {
            val result = withTimeout(10000) {
                network.fetchUsers()
            }
            Log.d("testinghaha","vvvv $result")
        } catch (error: Throwable) {
            Log.d("testinghaha","cccc $error")
            Log.d("testinghaha","${error.message}")
//            throw TitleRefreshError("Unable to refresh title", error)
        }
    }


    suspend fun fetchUserById(userResponseCallback: UserResponseCallback){
        withContext(Dispatchers.IO){
            try {
                val response = network.fetchUsers()
                if(response.isNotEmpty()){
                    user.postValue(response)
                    userResponseCallback.onSuccess()
                }
            }catch (t: Throwable) {
                when (t) {
                    is HttpException -> {
                        userResponseCallback.onError(t)
                    }
                }
            }
        }
    }
}

class TitleRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

interface UserResponseCallback{
    fun onSuccess():MutableLiveData<List<UserResponseItem>>
    fun onError(t:Throwable)
}