package com.example.peregrinario.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.peregrinario.dao.UserDao
import com.example.peregrinario.models.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class PeregrinarioDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: PeregrinarioDatabase? = null

        fun getDatabase(context: Context): PeregrinarioDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PeregrinarioDatabase::class.java,
                    "peregrinario_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}