package com.example.passgenertor.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordViewModel(application: Application):AndroidViewModel(application) {

    val readAllData: LiveData<List<Password>>
    private val repository:UserRepository

    init{
        val passwordDao = UserDatabase.getDatabase(application).passwordDao()
        repository = UserRepository(passwordDao)
        readAllData = repository.readAllData
    }
    fun addPassword(password: Password){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPassword(password)
            }
        }

    fun deletePassword(password: Password) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePassword(password)
        }
    }
}