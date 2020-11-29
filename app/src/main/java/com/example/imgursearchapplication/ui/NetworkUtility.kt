package com.example.imgursearchapplication.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import android.os.Build


class NetworkUtility(val context: Context) {

    fun registerNetworkCallback() {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        isNetworkConnected = true
                    }

                    override fun onLost(network: Network) {
                        isNetworkConnected = false
                    }
                }
                )
            } else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                isNetworkConnected = cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
            }
        } catch (e: Exception) {
           isNetworkConnected = false
        }
    }

    companion object {
        var isNetworkConnected = false
    }
}