package com.example.imgursearchapplication.ui

import com.example.imgursearchapplication.db.PhotoData

interface ImageClickCallback {
    fun onClick(photoData: PhotoData)
}