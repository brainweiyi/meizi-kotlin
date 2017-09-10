package com.mmdteam.beautygirl.ui.fragment

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.ImageView
import android.widget.Toast
import com.mmdteam.beautygirl.R
import com.mmdteam.beautygirl.adapter.HomeAdapter
import com.mmdteam.beautygirl.mvp.contract.PicContract
import com.mmdteam.beautygirl.mvp.model.bean.HomeBean
import com.mmdteam.beautygirl.mvp.presenter.HomePresenter
import com.mmdteam.beautygirl.ui.MainActivity
import com.mmdteam.beautygirl.utils.Utils
import com.mmdteam.imagewatcher.ImageWatcher
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_pic.*

/**
 * Created by brain on 2017/8/26.
 * 主界面
 */
class PicFragment : BaseFragment(),
        PicContract.View,
        ImageWatcher.OnPictureLongPressListener,
        HomeAdapter.CallBack,
        SwipeRefreshLayout.OnRefreshListener {


    private var mPresenter: HomePresenter? = null
    var mList = ArrayList<HomeBean.PicBean>()
    var mAdapter: HomeAdapter? = null

    var mImageWatcher: ImageWatcher? = null
    var page: Int = 1

    override fun setData(bean: HomeBean) {

        if (refreshLayout.isRefreshing) {
            page = 1
            refreshLayout.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }
        }
        page++

        bean.results.forEach {
            mList.add(it)
        }
        mAdapter?.notifyDataSetChanged()
    }


    override fun onRefresh() {
        mPresenter?.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun getLayoutResources(): Int {
        return R.layout.fragment_pic
    }


    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        lastPositions.forEach {
            if (it > max) {
                max = it
            }
        }
        return max;
    }

    var target: MyTarget? = null
    override fun initView() {
        homeRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mPresenter = HomePresenter(context, this)
        refreshLayout.setOnRefreshListener(this)
        mAdapter = HomeAdapter(context, mList)
        mAdapter?.setCallBack(this)
        homeRecyclerView.adapter = mAdapter
        homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView?.layoutManager as StaggeredGridLayoutManager
                val count = IntArray(layoutManager.spanCount)
                val last = layoutManager.findLastVisibleItemPositions(count)
                val lastPosition = findMax(last)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mList.size - 1) {
                    mPresenter!!.loadMore(page)
                }

            }

        })




        loader = ImageWatcher.Loader { context, url, lc ->
            if (target == null) {
                target = MyTarget(lc)
            }
            Picasso.with(context).load(url).into(target)
        }

        mImageWatcher = ImageWatcher.Helper.with(activity)
                .setTranslucentStatus(if ((activity as MainActivity).isTranslucentStatus) Utils.calcStatusBarHeight(activity) else 0)
                //.setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(this)
                .setLoader(loader)
                .create()

        refreshLayout.isRefreshing = true
        mPresenter?.start()

    }

    private var loader: ImageWatcher.Loader? = null

    override fun onThumbPictureClick(imageView: ImageView, url: String) {
        mImageWatcher?.show(imageView, url)
    }


    override fun onPictureLongPress(v: ImageView?, url: String?) {
        Toast.makeText(activity, "长按监听\n" + url, Toast.LENGTH_SHORT).show()
    }


    fun handleBackPressed(): Boolean {
        return mImageWatcher!!.handleBackPressed()
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