package com.mmdteam.beautygirl.mvp.presenter

import android.content.Context
import com.mmdteam.beautygirl.mvp.contract.PicContract
import com.mmdteam.beautygirl.mvp.model.PicModel
import com.mmdteam.beautygirl.mvp.model.bean.HomeBean
import com.mmdteam.beautygirl.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by brain on 2017/8/26.
 * Pic Presenter
 */
class PicPresenter(context: Context, view: PicContract.View, category: String) : PicContract.Presenter {
    var mContext: Context? = null;
    var mView: PicContract.View? = null;
    var mCategory: String? = null
    val mModel: PicModel by lazy {
        PicModel()
    }

    init {
        mContext = context;
        mView = view;
        mCategory = category
    }

    override fun start() {
        requestData(1)
    }

    fun loadMore(page: Int) {
        requestData(page)
    }

    override fun requestData(page: Int) {
        val observable: Observable<HomeBean>? = mContext?.let {
            mModel.loadData(it, mCategory!!, page)
        }
        observable?.applySchedulers()?.subscribe { homeBean: HomeBean ->
            mView?.setData(homeBean)
        }

    }


}