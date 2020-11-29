package com.example.imgursearchapplication.db

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PhotosObj(
    @SerializedName("data")
    val data: List<PhotoData>,
    @SerializedName("success")
    val sucess: Boolean,
    @SerializedName("status")
    val status: Int
) : Serializable