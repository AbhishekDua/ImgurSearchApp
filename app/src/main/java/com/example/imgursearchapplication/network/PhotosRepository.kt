package com.example.mysearchapplication.network

import com.example.imgursearchapplication.db.PhotosObj

interface PhotosRepository {
    suspend fun getPhotos(keyWord: String, pageNumber: Int = 1): PhotosObj
}