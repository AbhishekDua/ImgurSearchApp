package com.example.imgursearchapplication.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commentstable")
data class Comments(
    @PrimaryKey(autoGenerate = true)
    val commentId:Long = 0L,
    val imageId: String,
    val comment: String
)