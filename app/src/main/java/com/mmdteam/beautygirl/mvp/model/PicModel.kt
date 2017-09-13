package com.mmdteam.beautygirl.mvp.model

import android.content.Context
import com.mmdteam.beautygirl.mvp.model.bean.HomeBean
import com.mmdteam.beautygirl.network.ApiService
import com.mmdteam.beautygirl.network.RetrofitClient
import io.reactivex.Observable

/**
 * Created by brain on 2017/8/26.
 * 首页Model
 */
class PicModel {

    fun loadData(context: Context, category: String, page: Int): Observable<HomeBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getHomeData(category, page)
    }
}