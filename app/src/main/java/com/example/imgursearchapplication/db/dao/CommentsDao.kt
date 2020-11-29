package com.example.imgursearchapplication.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imgursearchapplication.db.entity.Comments

@Dao
interface CommentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comments: Comments)
    @Query("select comment from commentstable where imageId =:sImageId")
    fun getComments(sImageId: String): LiveData<List<String>>
}