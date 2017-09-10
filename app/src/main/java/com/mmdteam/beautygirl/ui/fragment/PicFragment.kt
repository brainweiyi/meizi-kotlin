package com.mmdteam.beautygirl.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.ImageView
import com.mmdteam.beautygirl.R
import com.mmdteam.beautygirl.adapter.PicAdapter
import com.mmdteam.beautygirl.mvp.contract.PicContract
import com.mmdteam.beautygirl.mvp.model.bean.HomeBean
import com.mmdteam.beautygirl.mvp.presenter.PicPresenter
import com.mmdteam.beautygirl.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_pic.*

/**
 * Created by brain on 2017/8/26.
 * 主界面
 */
class PicFragment : BaseFragment(),
        PicContract.View,
        PicAdapter.CallBack,
        SwipeRefreshLayout.OnRefreshListener {

    private var mPresenter: PicPresenter? = null
    var mList = ArrayList<HomeBean.PicBean>()
    var mAdapter: PicAdapter? = null

    var page: Int = 1
    lateinit var category: String

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
        category = arguments["category"] as String
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

    override fun initView() {
        homeRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mPresenter = PicPresenter(context, this, category)
        refreshLayout.setOnRefreshListener(this)
        mAdapter = PicAdapter(context, mList)
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

        refreshLayout.isRefreshing = true
        mPresenter?.start()

    }


    override fun onThumbPictureClick(imageView: ImageView, url: String) {
        (activity as MainActivity).mImageWatcher?.show(imageView, url)
    }


}