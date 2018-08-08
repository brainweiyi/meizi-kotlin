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

    private var cacheFileObserver: CacheFileObserver? = null

    override fun initView() {

        tv_bar_title.text = "设置"

        cacheSize.text = FileUtils.getCacheSize(this.activity!!.cacheDir, activity!!.externalCacheDir)



        cacheFileObserver = CacheFileObserver(this.activity!!.cacheDir.absolutePath)
        cacheFileObserver!!.startWatching()

        cleanCache.setOnClickListener {
            AlertDialog.Builder(this.activity!!)
                    .setTitle("清除缓存")
                    .setMessage("清除图片缓存")
                    .setPositiveButton("确定") { _, _ ->
                        FileUtils.cleanApplicationData(this.activity!!)
                        cacheSize.text = FileUtils.getCacheSize(this.activity!!.cacheDir, activity!!.externalCacheDir)
                    }.create().show()
        }
        versionName.text = getVersionName()
        checkUpdate.setOnClickListener {
            AlertDialog.Builder(this.activity!!)
                    .setTitle("检查更新")
                    .setMessage("已经是最新版")
                    .setPositiveButton("确定", { dialogInterface, _ ->
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


    private fun getVersionName(): String {
        return activity!!.packageManager.getPackageInfo(this.activity!!.packageName, 0).versionName
    }

    inner class CacheFileObserver(path: String?) : FileObserver(path) {

        override fun onEvent(event: Int, path: String?) {
            if (event == FileObserver.ALL_EVENTS) {
                cacheSize.text = FileUtils.getCacheSize(activity!!.cacheDir, activity!!.externalCacheDir)
            }
        }

    }
}