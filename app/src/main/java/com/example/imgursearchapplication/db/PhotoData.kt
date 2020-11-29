package com.example.imgursearchapplication.db

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class PhotoData(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("images")
    val images: List<Photos>? = null
): Serializable