package com.mmdteam.beautygirl.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by brain on 2017/9/10.
 * fragment pager adapter
 */
class HomePagerAdapter(fragmentManager: FragmentManager, private var fragments: ArrayList<Fragment>, private var titles: MutableList<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}