package com.mmdteam.beautygirl.ui.fragment

import android.os.FileObserver
import android.support.v7.app.AlertDialog
import com.mmdteam.beautygirl.R
import com.mmdteam.beautygirl.utils.FileUtils
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.layout_toolbar.*

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

        tv_bar_title.text = "设置"

        cacheSize.setText(FileUtils.getCacheSize(activity.cacheDir, activity.externalCacheDir))



        cacheFileObserver = CacheFileObserver(activity.cacheDir.absolutePath)
        cacheFileObserver!!.startWatching()

        cleanCache.setOnClickListener {
            AlertDialog.Builder(activity)
                    .setTitle("清除缓存")
                    .setMessage("清除图片缓存")
                    .setPositiveButton("确定", { dialogInterface, i ->
                        FileUtils.cleanApplicationData(activity)
                        cacheSize.setText(FileUtils.getCacheSize(activity.cacheDir, activity.externalCacheDir))
                    }).create().show()
        }
        versionName.setText(getVersionName())
        checkUpdate.setOnClickListener {
            AlertDialog.Builder(activity)
                    .setTitle("检查更新")
                    .setMessage("已经是最新版")
                    .setPositiveButton("确定", { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }).create().show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (cacheFileObserver != null) {
            cacheFileObserver!!.stopWatching()
        }
    }


    fun getVersionName(): String {
        return activity.packageManager.getPackageInfo(activity.packageName, 0).versionName
    }

    inner class CacheFileObserver(path: String?) : FileObserver(path) {

        override fun onEvent(event: Int, path: String?) {
            if (event == FileObserver.ALL_EVENTS) {
                cacheSize.setText(FileUtils.getCacheSize(activity.cacheDir, activity.externalCacheDir))
            }
        }

    }
}