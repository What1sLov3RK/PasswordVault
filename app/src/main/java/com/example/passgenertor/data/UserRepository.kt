package com.example.passgenertor.data

import androidx.lifecycle.LiveData

class UserRepository(private val passwordDao: PasswordDao) {

    val readAllData: LiveData<List<Password>> = passwordDao.readAllData()

    suspend fun addPassword(password: Password){
        passwordDao.addPassword(password)
    }
    suspend fun deletePassword(password: Password) {
        passwordDao.deletePassword(password)
    }
}