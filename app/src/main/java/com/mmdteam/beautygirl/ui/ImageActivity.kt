package com.mmdteam.beautygirl.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mmdteam.beautygirl.R
import com.mmdteam.beautygirl.adapter.ImageAdapter
import com.mmdteam.beautygirl.mvp.model.bean.HomeBean
import kotlinx.android.synthetic.main.activity_image.*
import java.util.*

/**
 * Created by brain on 2017/8/29.
 * 显示图片详情的Activity
 */
class ImageActivity : AppCompatActivity() {

    //    var bean: HomeBean.PicBean? = null
    var list: ArrayList<HomeBean.PicBean>? = null
    var index: Int? = 0
    var viewAdapter: ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
//        bean = intent.getParcelableExtra<HomeBean.PicBean>("data")
        list = intent.getParcelableArrayListExtra<HomeBean.PicBean>("data")
        index = intent.getIntExtra("index", 0);
//        list?.get(index!!)
        viewAdapter = ImageAdapter(this, list!!,index!!)
        imageViewPager.adapter = viewAdapter

    }
}