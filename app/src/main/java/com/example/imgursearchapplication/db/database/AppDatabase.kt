package com.example.imgursearchapplication.db.database

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imgursearchapplication.db.dao.CommentsDao
import com.example.imgursearchapplication.db.entity.Comments

@Database(entities = [Comments::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun commentsDao(): CommentsDao

    companion object {
        private var sInstance: AppDatabase? = null

        @VisibleForTesting
        val DATABASE_NAME = "basic-sample-db"

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return sInstance as AppDatabase
        }
    }

}