package com.mmdteam.beautygirl.network

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by brain on 2017/8/27.
 *
 * 网络相关日志
 */
class HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String?) {
        Log.d("HttpLogInfo", message)
    }
}