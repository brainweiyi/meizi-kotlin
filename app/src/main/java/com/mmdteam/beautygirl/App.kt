package com.mmdteam.beautygirl

import android.app.Application
import android.content.Intent
import com.jakewharton.picasso.OkHttp3Downloader
import com.mmdteam.beautygirl.ui.LockActivity
import com.mmdteam.beautygirl.utils.ForegroundCallbacks
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI
import com.squareup.picasso.Picasso
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.*

/**
 * Created by brain on 2017/8/25.
 * Application
 */
class App : Application(), ForegroundCallbacks.Listener {
    override fun onBecameForeground() {
        val intent = Intent(applicationContext, LockActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onBecameBackground() {
    }


    override fun onCreate() {
        super.onCreate()

        ForegroundCallbacks.init(this)
        ForegroundCallbacks.get().addListener(this)
//        initPicasso()
        initSensorsData()
    }

    fun initSensorsData() {
        val DATA_URL = "http://sdk-test.cloud.sensorsdata.cn:8006/sa?project=weiyi&token=95c73ae661f85aa0"
        val debugMode: SensorsDataAPI.DebugMode = if (BuildConfig.DEBUG) SensorsDataAPI.DebugMode.DEBUG_AND_TRACK else SensorsDataAPI.DebugMode.DEBUG_OFF
        SensorsDataAPI.sharedInstance(this, DATA_URL, debugMode)
        try {
            val eventTypeList = ArrayList<SensorsDataAPI.AutoTrackEventType>()
            // $AppStart
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_START)
            // $AppEnd
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_END)
            // $AppViewScreen
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN)
            // $AppClick
            eventTypeList.add(SensorsDataAPI.AutoTrackEventType.APP_CLICK)
            SensorsDataAPI.sharedInstance().enableAutoTrack(eventTypeList)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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