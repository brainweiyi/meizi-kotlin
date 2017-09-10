package com.mmdteam.beautygirl.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.mmdteam.beautygirl.R
import com.mmdteam.beautygirl.adapter.HomePagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by brain on 2017/9/10.
 * 首页
 */
class HomeFragment : BaseFragment() {

    var fragments: ArrayList<Fragment>? = null
    var titles = listOf<String>("全部", "大胸", "翘臀", "黑丝", "美腿", "清新", "杂烩").toMutableList()
    var categories = arrayOf("All", "DaXiong", "QiaoTun", "HeiSi", "MeiTui", "QingXin", "ZaHui")

    var adapter: HomePagerAdapter? = null

    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        initFragment()
        tabs.setupWithViewPager(homeViewPager, true)
    }

    private fun initFragment() {
        fragments = ArrayList()
        titles.forEachIndexed { index, s ->
            val fragment = PicFragment()
            val bundle = Bundle()
            bundle.putString("category", categories[index])
            fragment.arguments = bundle
            fragments!!.add(index, fragment)
        }
        adapter = HomePagerAdapter(fragmentManager, fragments!!, titles)
        homeViewPager.adapter = adapter

    }




}