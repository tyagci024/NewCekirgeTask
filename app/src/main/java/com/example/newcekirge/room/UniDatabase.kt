package com.example.newcekirge.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newcekirge.model.University

@Database(entities = [University::class], version = 3)
abstract class UniDatabase : RoomDatabase() {

    abstract fun uniDao(): UniDao

    companion object {
        @Volatile
        private var INSTANCE: UniDatabase? = null

        fun getDatabase(context: Context): UniDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UniDatabase::class.java,
                    "university_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}