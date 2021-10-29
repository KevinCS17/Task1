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

    private val _snackBar = MutableLiveData<String?>()

    val snackbar: LiveData<String?>
        get() = _snackBar

    private val _spinner = MutableLiveData<Boolean>(false)


    fun onSnackbarShown() {
        _snackBar.value = null
    }


    private fun launchDataLoad(block: suspend () -> Unit): Unit {
        viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: TitleRefreshError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }

    val name = repository.user
    fun onButtonSearch(string: String){
        searchUser(string)
//        Log.d("testkevin","view model pressed $string")
    }

    private fun searchUser(string: String) = launchDataLoad{
        repository.fetchUsers()
    }




}
