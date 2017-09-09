package com.mmdteam.beautygirl.mvp.model.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by brain on 2017/8/26.
 */
data class HomeBean(var category: String?,
                    var page: Int,
                    var results: List<PicBean>) {

    data class PicBean(var category: String?,
                       var group_url: String?,
                       var image_url: String?,
                       var objectId: String?,
                       var thumb_url: String?,
                       var title: String?) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(category)
            parcel.writeString(group_url)
            parcel.writeString(image_url)
            parcel.writeString(objectId)
            parcel.writeString(thumb_url)
            parcel.writeString(title)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<PicBean> {
            override fun createFromParcel(parcel: Parcel): PicBean {
                return PicBean(parcel)
            }

            override fun newArray(size: Int): Array<PicBean?> {
                return arrayOfNulls(size)
            }
        }
    }
}