package com.mmdteam.beautygirl.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View

/**
 * Created by brain on 2017/9/8.
 */
object Utils {
    fun fitsSystemWindows(isTranslucentStatus: Boolean, view: View) {
        if (isTranslucentStatus) {
            view.getLayoutParams().height = calcStatusBarHeight(view.getContext())
        }
    }

    @SuppressLint("PrivateApi")
    fun calcStatusBarHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height").get(`object`).toString())
            statusHeight = context.getResources().getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusHeight
    }
}