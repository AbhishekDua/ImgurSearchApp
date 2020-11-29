package com.example.imgursearchapplication.network

import com.example.imgursearchapplication.db.PhotosObj
import com.example.mysearchapplication.network.PhotosRepository
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Implementation of [RemoteRepository] using retrofit library
 */
class ImgurPhotosApi : PhotosRepository {

    val imgurPhotoService: GetPhotosService by lazy { retrofit.create(GetPhotosService::class.java) }

    override suspend fun getPhotos(keyWord: String,pageNumber:Int) : PhotosObj {
        return  imgurPhotoService.getPhotos(text = keyWord, page = pageNumber)
    }


    companion object {
        private const val BASE_URL = "https://api.imgur.com/3/"

        private val gson = GsonBuilder().create()

        private val retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL).build()

    }

}