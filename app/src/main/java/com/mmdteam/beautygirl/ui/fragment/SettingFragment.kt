package com.mmdteam.beautygirl.ui.fragment

import android.os.FileObserver
import com.mmdteam.beautygirl.R
import com.mmdteam.beautygirl.utils.FileUtils
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * Created by brain on 2017/8/26.
 * 设置界面
 */
class SettingFragment : BaseFragment() {
    override fun getLayoutResources(): Int {
        return R.layout.fragment_setting
    }

    var cacheFileObserver: CacheFileObserver? = null

    override fun initView() {

        cacheSize.setText(FileUtils.getCacheSize(activity.cacheDir, activity.externalCacheDir))



        cacheFileObserver = CacheFileObserver(activity.cacheDir.absolutePath)
        cacheFileObserver!!.startWatching()

        cleanCache.setOnClickListener {
            FileUtils.cleanApplicationData(activity)
            cacheSize.setText(FileUtils.getCacheSize(activity.cacheDir, activity.externalCacheDir))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (cacheFileObserver != null) {
            cacheFileObserver!!.stopWatching()
        }
    }

    inner class CacheFileObserver(path: String?) : FileObserver(path) {

        override fun onEvent(event: Int, path: String?) {
            if (event == FileObserver.ALL_EVENTS) {
                cacheSize.setText(FileUtils.getCacheSize(activity.cacheDir, activity.externalCacheDir))
            }
        }

    }
}