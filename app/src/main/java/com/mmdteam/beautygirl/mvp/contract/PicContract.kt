package com.mmdteam.beautygirl.mvp.contract

import com.mmdteam.beautygirl.base.BasePresenter
import com.mmdteam.beautygirl.base.BaseView
import com.mmdteam.beautygirl.mvp.model.bean.HomeBean

/**
 * Created by brain on 2017/8/26.
 * Contract
 */
interface PicContract {

    interface View : BaseView<Presenter> {
        fun setData(bean: HomeBean)
    }

    interface Presenter : BasePresenter {
        fun requestData(page: Int);
    }
}