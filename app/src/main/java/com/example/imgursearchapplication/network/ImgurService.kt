package com.example.imgursearchapplication.network

import com.example.imgursearchapplication.db.PhotosObj
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

//keeps changing
const val API_KEY = "Client-ID 137cda6b5008a7c"

interface GetPhotosService {

    @GET("gallery/search/1?q_type=jpg|png")
    suspend fun getPhotos(
        @Header("Authorization")value: String = API_KEY,
        @Query("q") text: String,
        @Query("page") page: Int
    ): PhotosObj
}