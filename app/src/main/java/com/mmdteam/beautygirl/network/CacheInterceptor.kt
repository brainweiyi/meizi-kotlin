package com.mmdteam.beautygirl.network

import android.content.Context
import android.util.Log
import com.mmdteam.beautygirl.utils.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
/**
 * Created by brain on 2017/8/25.
 * 网络缓存
 */
class CacheInterceptor(var context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        return if (NetworkUtils.isNetConnected(context)) {
            val response = chain?.proceed(request)
            // read from cache for 60 s
            val maxAge = 60
            val cacheControl = request?.cacheControl().toString()
            Log.e("CacheInterceptor", "6s load cache" + cacheControl)
            response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, max-age=" + maxAge)?.build()
        } else {
            Log.e("CacheInterceptor", " no network load cache")
            request = request?.newBuilder()?.cacheControl(CacheControl.FORCE_CACHE)?.build()
            val response = chain?.proceed(request)
            //set cahe times is 3 days
            val maxStale = 60 * 60 * 24 * 3
            response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)?.build()
        }
    }
}