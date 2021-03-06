package com.mmdteam.beautygirl.network

import com.mmdteam.beautygirl.mvp.model.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by brain on 2017/8/25.
 * api service
 */
interface ApiService {

    companion object {
        val BASE_URL: String
            get() = "https://meizi.leanapp.cn/"
    }

    //获取首页第一页数据
    @GET("category/{category}/page/{page}")
    fun getHomeData(@Path("category") category: String, @Path("page") page: Int): Observable<HomeBean>
}