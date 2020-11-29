package com.example.mysearchapplication

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.imgursearchapplication.db.PhotoData
import com.example.mysearchapplication.network.PhotosRepository
import kotlinx.coroutines.CoroutineScope


class PhotoDataSourceFactory(
    val repository: PhotosRepository,
    val query: String,
    val scope: CoroutineScope,
    val photoNetworkStatus: MutableLiveData<String>
) : DataSource.Factory<Int, PhotoData>() {

    override fun create(): DataSource<Int, PhotoData> {
        val dataSource = PhotoDataSource(query, repository, scope, photoNetworkStatus)
        return dataSource
    }

}