package com.example.mysearchapplication

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.imgursearchapplication.db.PhotoData
import com.example.imgursearchapplication.ui.NetworkUtility
import com.example.mysearchapplication.network.PhotosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PhotoDataSource(
    val currentQuery: String,
    val repository: PhotosRepository,
    val scope: CoroutineScope,
    val photoNetworkStatus: MutableLiveData<String>
) : PageKeyedDataSource<Int, PhotoData>() {

    val FIRST_PAGE = 1;
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoData>
    ) {
        scope.launch {
            if (NetworkUtility.isNetworkConnected) {
                val response = repository.getPhotos(currentQuery, FIRST_PAGE)
                if (response.sucess) {
                    callback.onResult(response.data, null, FIRST_PAGE + 1)
                }
                updateNetworkStatus(response.sucess, response.status)
            } else {
                updateNetworkStatus(false, 400)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoData>) {
        scope.launch {
            if(NetworkUtility.isNetworkConnected) {
                val response = repository.getPhotos(currentQuery, params.key)
                if (response.sucess) {
                    callback.onResult(response.data, params.key + 1)
                }
                updateNetworkStatus(response.sucess, response.status)
            } else {
                updateNetworkStatus(false, 400)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoData>) {
        scope.launch {
            if(NetworkUtility.isNetworkConnected) {
                val response = repository.getPhotos(currentQuery, params.key)
                if (response.sucess) {
                    val keyToReturn = if (params.key > 1) {
                        params.key - 1
                    } else {
                        1
                    }
                    callback.onResult(response.data, keyToReturn)
                }
                updateNetworkStatus(response.sucess, response.status)
            } else {
                updateNetworkStatus(false, 400)
            }
        }
    }

    private fun updateNetworkStatus(status: Boolean, code:Int) {
        val message = "status:${status} and code:${code}"
        photoNetworkStatus.postValue(message)
    }

}