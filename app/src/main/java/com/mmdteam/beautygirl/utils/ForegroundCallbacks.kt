package com.mmdteam.beautygirl.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by brain on 2017/9/15.
 * ForegroundCallbacks
 */
class ForegroundCallbacks : Application.ActivityLifecycleCallbacks {

    private val CHECK_DELAY = 500L

    private var foreground = false
    private var paused = true
    private var handle: Handler = Handler()
    private var listeners = CopyOnWriteArrayList<Listener>()
    private var check: Runnable? = null

    interface Listener {
        fun onBecameForeground()
        fun onBecameBackground()
    }


    companion object {
        private var instance: ForegroundCallbacks? = null
        fun init(application: Application): ForegroundCallbacks {
            if (instance == null) {
                instance = ForegroundCallbacks()
                application.registerActivityLifecycleCallbacks(instance)
            }
            return instance!!
        }

        fun get(application: Application): ForegroundCallbacks {
            if (instance == null) {
                init(application)
            }
            return instance!!
        }

        fun get(context: Context): ForegroundCallbacks {
            if (instance == null) {
                if (context.applicationContext is Application) {
                    init(context.applicationContext as Application)
                }
            }
            return instance!!
        }

        fun get(): ForegroundCallbacks {
            if (instance == null) {
                throw IllegalStateException(
                        "Foreground is not initialised - invoke " + "at least once with parameterised init/get")
            }
            return instance!!
        }
    }

    fun isForeground(): Boolean {
        return foreground
    }

    fun isBackground(): Boolean {
        return !foreground
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    override fun onActivityPaused(activity: Activity?) {
        paused = true
        if (check != null) {
            handle.removeCallbacks(check)
        }
        check = Runnable {
            run {
                if (foreground and paused) {
                    foreground = false
                    listeners.forEach {
                        try {
                            it.onBecameBackground()
                        } catch (e: Exception) {
                            throw Exception("Listener threw exception!:" + e.toString())
                        }
                    }
                }
            }
        }
        handle.postDelayed(check, CHECK_DELAY)
    }

    override fun onActivityResumed(activity: Activity?) {
        paused = false
        val wasBackground = !foreground
        foreground = true

        if (check != null) {
            handle.removeCallbacks(check)
        }
        if (wasBackground) {
            listeners.forEach {
                try {
                    it.onBecameForeground()
                } catch (e: Exception) {
                    throw Exception("Listener threw exception!:" + e.toString())
                }
            }
        } else {

        }
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
    }
}