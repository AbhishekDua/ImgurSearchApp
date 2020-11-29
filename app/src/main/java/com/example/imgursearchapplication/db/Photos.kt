package com.example.imgursearchapplication.db

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Photos(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("link")
    val link: String? = null
): Serializable