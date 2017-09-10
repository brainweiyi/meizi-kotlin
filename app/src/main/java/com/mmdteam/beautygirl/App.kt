package com.mmdteam.beautygirl

import android.app.Application
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

/**
 * Created by brain on 2017/8/25.
 * Application
 */
class App : Application() {


    override fun onCreate() {
        super.onCreate()
        initPicasso()
    }

    /**
     * 改变毕卡索的缓存位置
     */
    private fun initPicasso() {
        val httpCacheDirectory = File(this.cacheDir.absolutePath + "/image-cache")
        val cache = Cache(httpCacheDirectory, 50 * 1024 * 1024)//最大缓存大小50MB
        val okHttpClient = OkHttpClient.Builder().cache(cache).build()
        val picasso = Picasso.Builder(applicationContext).downloader(OkHttp3Downloader(okHttpClient)).build()
        Picasso.setSingletonInstance(picasso)
    }
}