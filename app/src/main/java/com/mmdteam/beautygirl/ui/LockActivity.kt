package com.mmdteam.beautygirl.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mmdteam.beautygirl.R
import com.mmdteam.numberlocker.NumberAdapter
import kotlinx.android.synthetic.main.activity_number_locker.*

/**
 * Created by brain on 2017/9/16.
 * 数字锁界面
 */
class LockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_locker)
        passwordKey.setListener {
            if (it) {
                finish()
            }
        }
    }
}