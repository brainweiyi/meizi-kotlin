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
        cache.setText(FileUtils.getCacheSize(activity.cacheDir, activity.externalCacheDir) +
                "\n" +
                activity.cacheDir.absolutePath +
                "\n" +
                activity.externalCacheDir.absolutePath +
                "\n" +
                activity.filesDir.absolutePath)

        cacheFileObserver = CacheFileObserver(activity.cacheDir.absolutePath)
        cacheFileObserver!!.startWatching()

        cache.setOnClickListener {
            FileUtils.cleanApplicationData(activity)
            cache.setText(FileUtils.getCacheSize(activity.cacheDir, activity.externalCacheDir) +
                    "\n" +
                    activity.cacheDir.absolutePath +
                    "\n" +
                    activity.externalCacheDir.absolutePath)
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
                cache.setText(FileUtils.getCacheSize(activity.cacheDir, activity.externalCacheDir) +
                        "\n" +
                        activity.cacheDir.absolutePath +
                        "\n" +
                        activity.externalCacheDir.absolutePath +
                        "\n" +
                        activity.filesDir.absolutePath)
            }
        }

    }
}