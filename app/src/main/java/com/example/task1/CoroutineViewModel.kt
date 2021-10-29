package com.example.task1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CoroutineViewModel(private val repository: CoroutineRepository) : ViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::CoroutineViewModel)
    }

    private val _spinner = MutableLiveData<Boolean>(false)

    private fun launchDataLoad(block: suspend () -> Unit): Unit {
        viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: TitleRefreshError) {
            } finally {
                _spinner.value = false
            }
        }
    }

    val data = repository.data

    fun onButtonSearch(string: String){
        searchUser(string)
    }

    private fun searchUser(string: String) = launchDataLoad{
        repository.fetchUsersById(string)
    }
}
