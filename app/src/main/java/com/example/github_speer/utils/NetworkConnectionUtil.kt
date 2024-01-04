package com.example.github_speer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkConnectionUtil @Inject constructor(@ApplicationContext var context: Context) {
    /**
     * This function checks if the mobile has turned on any Internet Service or not.
     * If not it returns false, else true.
     * */
    fun isConnectedToInternet(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return ((capabilities != null) && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR)))
    }
}