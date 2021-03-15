package com.zed.kz.doskaz.entity

import android.os.Parcel
import android.os.Parcelable

data class ListItem(
    var id: Int? = null,
    var title: String?,
    var requestCode: Int,
    var selected: Boolean = false,
    var type: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readInt(),
        1 == source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id ?: 0)
        writeString(title)
        writeInt(requestCode)
        writeInt((if (selected) 1 else 0))
        writeString(type)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ListItem> = object : Parcelable.Creator<ListItem> {
            override fun createFromParcel(source: Parcel): ListItem = ListItem(source)
            override fun newArray(size: Int): Array<ListItem?> = arrayOfNulls(size)
        }
    }
}