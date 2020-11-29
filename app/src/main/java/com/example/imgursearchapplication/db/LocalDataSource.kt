package com.example.imgursearchapplication.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.imgursearchapplication.db.database.AppDatabase
import com.example.imgursearchapplication.db.entity.Comments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocalDataSource (context: Context): DataSource {
    private val database = AppDatabase.getInstance(context)

    override fun addComment(imageId: String, comment: String) {
        GlobalScope.launch(Dispatchers.IO) {
            database.commentsDao().insertComment(Comments(imageId = imageId,comment = comment))
        }
    }

    override fun getComments(imageId: String): LiveData<List<String>> {
        return database.commentsDao().getComments(imageId)
    }
}

interface DataSource {
    fun addComment(imageId: String, comment: String)
    fun getComments(imageId: String): LiveData<List<String>>
}