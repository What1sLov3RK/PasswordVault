package com.example.passgenertor.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="user_data" )
data class Password (
    @PrimaryKey(autoGenerate=true)
    val id: Int,
    val password: String,
    val url: String,
)