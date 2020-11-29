package com.example.imgursearchapplication.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.imgursearchapplication.db.PhotoData
import com.example.imgursearchapplication.network.ImgurPhotosApi
import com.example.mysearchapplication.PhotoDataSource
import com.example.mysearchapplication.PhotoDataSourceFactory
import com.example.mysearchapplication.network.PhotosRepository

class PhotosViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val QUERY = "QUERY"
    private val savedStateHandle: SavedStateHandle
    val photoList: LiveData<PagedList<PhotoData>>
    val repository: PhotosRepository
    var photoNetworkStatus = MutableLiveData<String>()

    init {
        this.savedStateHandle = savedStateHandle
        repository = ImgurPhotosApi()
        photoList =  Transformations.switchMap(savedStateHandle.getLiveData(QUERY, "")) {
             initializePhotoListLiveData(it)
        }
    }

    fun setQuery(query: String) {
        if (query.isNotEmpty()) {
            savedStateHandle[QUERY] = query
        }
    }

    private fun initializePhotoListLiveData(query: String): LiveData   <PagedList<PhotoData>> {
        val config = PagedList.Config.Builder()
            .setPageSize(36)
            .setPrefetchDistance(20)
            .setEnablePlaceholders(false)
            .build()
        val dataSource = PhotoDataSourceFactory(repository, query, viewModelScope, photoNetworkStatus)
        return LivePagedListBuilder(dataSource, config).build()
    }

}