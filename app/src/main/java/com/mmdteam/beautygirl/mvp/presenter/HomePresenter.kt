package com.mmdteam.beautygirl.mvp.presenter

import android.content.Context
import com.mmdteam.beautygirl.mvp.contract.PicContract
import com.mmdteam.beautygirl.mvp.model.PicModel
import com.mmdteam.beautygirl.mvp.model.bean.HomeBean
import com.mmdteam.beautygirl.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by brain on 2017/8/26.
 */
class HomePresenter(context: Context, view: PicContract.View) : PicContract.Presenter {
    var mContext: Context? = null;
    var mView: PicContract.View? = null;
    val mModel: PicModel by lazy {
        PicModel()
    }

    init {
        mContext = context;
        mView = view;
    }

    override fun start() {
        requestData(1)
    }

    fun loadMore(page: Int) {
        requestData(page)
    }

    override fun requestData(page: Int) {
        val observable: Observable<HomeBean>? = mContext?.let {
            mModel.loadData(it, page)
        }
        observable?.applySchedulers()?.subscribe { homeBean: HomeBean ->
            mView?.setData(homeBean)
        }

    }


}