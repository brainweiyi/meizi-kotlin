package com.mmdteam.beautygirl.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by brain on 2017/8/25.
 * retrofit客户端
 */
class RetrofitClient private constructor(context: Context, baseUrl: String) {
    private var httpCacheDirectory: File? = null
    private var cache: Cache? = null
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var DEFAULT_TIMEOUT: Long = 20

    init {
        //缓存地址
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(context.cacheDir, "app_cache")
        }
        try {
            if (cache == null) {
                cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
            }
        } catch (e: Exception) {
            Log.e("OKHttp", "Could not create http cache", e)
        }
        //OKHttp创建
        okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor(HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .addInterceptor(CacheInterceptor(context))
                .addNetworkInterceptor(CacheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()
        //retrofit创建
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: RetrofitClient? = null

        fun getInstance(context: Context, baseUrl: String): RetrofitClient {
            if (instance == null) {
                synchronized(RetrofitClient::class) {
                    if (instance == null) {
                        instance = RetrofitClient(context, baseUrl)
                    }
                }
            }
            return instance!!
        }
    }

    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!!")
        }
        return retrofit?.create(service)
    }
}