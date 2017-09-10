package com.mmdteam.beautygirl.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.mikepenz.iconics.IconicsDrawable
import com.mmdteam.beautygirl.R
import com.mmdteam.beautygirl.ui.fragment.HomeFragment
import com.mmdteam.beautygirl.ui.fragment.SettingFragment
import com.mmdteam.beautygirl.utils.Utils
import com.mmdteam.imagewatcher.ImageWatcher
import com.mmdteam.navmenu.NavMenuLayout
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        NavMenuLayout.OnItemSelectedListener,
        NavMenuLayout.OnItemReSelectedListener,
        ImageWatcher.OnPictureLongPressListener {

    private var homeFragment: HomeFragment? = null
    private var settingFragment: SettingFragment? = null


    override fun onItemReSelected(position: Int) {
        tv_bar_title.setText("又一次点击了" + position)
    }

    override fun onItemSelected(position: Int) {
        switchFragment(position)
    }


    private var iconTitle = arrayOf("首页", "设置")

    private var isTranslucentStatus = false

    private var target: MyTarget? = null
    private var loader: ImageWatcher.Loader? = null
    var mImageWatcher: ImageWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
//            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            isTranslucentStatus = true
        }
        val listIcon = arrayOf<Drawable>(
                IconicsDrawable(this).icon("ion-ios-home-outline").color(Color.BLACK).sizeDp(22),
                IconicsDrawable(this).icon("ion-ios-gear-outline").color(Color.BLACK).sizeDp(22))
        val listIconSelected = arrayOf<Drawable>(
                IconicsDrawable(this).icon("ion-ios-home").color(Color.BLUE).sizeDp(22),
                IconicsDrawable(this).icon("ion-ios-gear").color(Color.BLUE).sizeDp(22))
        nav_layout
                .setIcons(listIcon)
                .setIconsSelected(listIconSelected)
                .setTextRes(iconTitle)
                .setTextColor(Color.BLACK)
                .setTextColorSelected(Color.BLUE)
                .setMarginTop(4)
                .setTextSize(10)
                .setSelected(0)
        nav_layout.setOnItemSelectedListener(this)
        nav_layout.setOnItemReSelectedListener(this)

        initFragment(savedInstanceState)

        Utils.fitsSystemWindows(isTranslucentStatus, v_fit)



        loader = ImageWatcher.Loader { context, url, lc ->
            if (target == null) {
                target = MyTarget(lc)
            }
            Picasso.with(context).load(url).into(target)
        }

        mImageWatcher = ImageWatcher.Helper.with(this)
                .setTranslucentStatus(if (isTranslucentStatus) Utils.calcStatusBarHeight(this) else 0)
                //.setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(this)
                .setLoader(loader)
                .create()


    }

    override fun onPictureLongPress(imageView: ImageView?, url: String?) {

    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val mFragments: List<Fragment> = supportFragmentManager.fragments
            for (item in mFragments) {
                if (item is HomeFragment) {
                    homeFragment = item
                }
                if (item is SettingFragment) {
                    settingFragment = item
                }
            }

        } else {
            homeFragment = HomeFragment()
            settingFragment = SettingFragment()
            val fragmentTrans = supportFragmentManager.beginTransaction()
            fragmentTrans.add(R.id.fl_content, homeFragment)
            fragmentTrans.add(R.id.fl_content, settingFragment)
            fragmentTrans.commit()
        }
        supportFragmentManager.beginTransaction()
                .show(homeFragment)
                .hide(settingFragment).commit()
    }

    private fun switchFragment(position: Int) {
        when (position) {
            0 -> {
                supportFragmentManager.beginTransaction()
                        .show(homeFragment)
                        .hide(settingFragment).commit()
                tv_bar_title.text = "首页"
            }
            1 -> {

                supportFragmentManager.beginTransaction()
                        .show(settingFragment)
                        .hide(homeFragment).commit()
                tv_bar_title.text = "设置"
            }
        }
    }

    override fun onBackPressed() {

        if (!mImageWatcher!!.handleBackPressed()) {
            super.onBackPressed()
        }

    }


    class MyTarget(lc: ImageWatcher.LoadCallback) : Target {

        private var lc: ImageWatcher.LoadCallback? = null

        init {
            this.lc = lc
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            lc?.onLoadStarted(placeHolderDrawable)
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
            lc?.onLoadFailed(errorDrawable)
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            lc?.onResourceReady(bitmap)
        }

    }
}
