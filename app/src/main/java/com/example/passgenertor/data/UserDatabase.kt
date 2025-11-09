package com.example.passgenertor.data

import androidx.room.RoomDatabase
import androidx.room.Database
import android.content.Context
import androidx.room.Room

@Database(entities= [Password:: class], version=1, exportSchema = false)
abstract class UserDatabase:RoomDatabase() {
    abstract fun passwordDao(): PasswordDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context:Context): UserDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}