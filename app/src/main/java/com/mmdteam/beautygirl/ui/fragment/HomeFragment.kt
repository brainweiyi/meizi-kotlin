package com.mmdteam.beautygirl.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.mmdteam.beautygirl.R
import com.mmdteam.beautygirl.adapter.HomePagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by brain on 2017/9/10.
 * 首页
 */
class HomeFragment : BaseFragment() {

    private var fragments: ArrayList<Fragment>? = null
    private var titles = listOf<String>("全部", "大胸", "翘臀", "黑丝", "美腿", "清新", "杂烩").toMutableList()
    private var categories = arrayOf("All", "DaXiong", "QiaoTun", "HeiSi", "MeiTui", "QingXin", "ZaHui")

    private var adapter: HomePagerAdapter? = null

    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        tv_bar_title.text = "首页"
        initFragment()
        tabs.setupWithViewPager(homeViewPager, true)
    }

    private fun initFragment() {
        fragments = ArrayList()

        categories.forEach {
            val fragment = PicFragment()
            val bundle = Bundle()
            bundle.putString("category", it)
            fragment.arguments = bundle
            fragments!!.add(fragment)
        }

        adapter = HomePagerAdapter(this.fragmentManager!!, fragments!!, titles)
        homeViewPager.adapter = adapter

    }


}