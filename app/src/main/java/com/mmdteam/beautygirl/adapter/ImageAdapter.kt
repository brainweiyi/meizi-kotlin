package com.mmdteam.beautygirl.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mmdteam.beautygirl.R
import com.mmdteam.beautygirl.mvp.model.bean.HomeBean
import com.squareup.picasso.Picasso

/**
 * Created by brain on 2017/8/29.
 */
class ImageAdapter(context: Context, images: ArrayList<HomeBean.PicBean>?, index: Int) : PagerAdapter() {

    var context: Context? = null
    var images: List<HomeBean.PicBean>? = null
    var cacheView: SparseArray<View>? = null
    var containerTemp: ViewGroup? = null
    var index:Int = 0

    init {
        this.context = context
        this.images = images
        cacheView = SparseArray(images!!.size)
    }


    override fun instantiateItem(container: ViewGroup?, position: Int): Any {

        if (containerTemp == null) {
            containerTemp = container
        }

        var view: View? = cacheView?.get(position)
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_image, container, false)
            val bigImage = view!!.findViewById<ImageView>(R.id.bigImage)
            Picasso.with(context).load(images?.get(position)?.image_url).into(bigImage)
        }
        container?.addView(view)
        return view

    }


    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        val view: View? = `object` as View?
        container?.removeView(view)
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return images?.size!!
    }

    override fun getItemPosition(`object`: Any?): Int {
        return super.getItemPosition(`object`)
    }


}