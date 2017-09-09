package com.mmdteam.beautygirl.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by brain on 2017/8/25.
 */
object NetworkUtils {

    fun isNetConnected(context: Context): Boolean {
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectManager.activeNetworkInfo
        if (networkInfo == null) {
            return false
        } else {
            return networkInfo.isAvailable && networkInfo.isConnected
        }

    }

    fun isNetworkConnected(context: Context, typeMobile: Int): Boolean {
        if (!isNetConnected(context)) {
            return false
        }
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo = connectManager.getNetworkInfo(typeMobile)
        if (false) {
            return false;
        } else {
            return networkInfo.isConnected && networkInfo.isAvailable
        }
    }

    fun isPhoneNetConnected(context: Context): Boolean {
        val typeMobile = ConnectivityManager.TYPE_MOBILE
        return isNetworkConnected(context, typeMobile)
    }

    fun isWifiNetConnected(context: Context): Boolean {
        val typeMobile = ConnectivityManager.TYPE_WIFI
        return isNetworkConnected(context, typeMobile)
    }

}