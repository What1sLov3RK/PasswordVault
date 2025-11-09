package com.example.passgenertor.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPassword(password: Password)

    @Query("SELECT * FROM user_data ORDER BY id ASC")
    fun readAllData(): LiveData<List<Password>>

    @Delete()
    suspend fun deletePassword(password: Password)
}