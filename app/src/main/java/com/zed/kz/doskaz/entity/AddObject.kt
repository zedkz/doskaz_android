package com.zed.kz.doskaz.entity

import android.os.Parcel
import android.os.Parcelable

data class AddObject(
    var index: Int = 1,
    var title: String = "",
    var value: String? = null,
    var valueToUpload: String? = null,
    var type: Int = TYPE_CONTENT,
    var key: Int? = null
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString() ?: "",
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(index)
        writeString(title)
        writeString(value)
        writeString(valueToUpload)
        writeInt(type)
        writeInt(key ?: 0)
    }

    companion object {

        const val TYPE_HEADER = 1
        const val TYPE_HEADER_SMALL = 2
        const val TYPE_CONTENT = 3

        @JvmField
        val CREATOR: Parcelable.Creator<AddObject> = object : Parcelable.Creator<AddObject> {
            override fun createFromParcel(source: Parcel): AddObject = AddObject(source)
            override fun newArray(size: Int): Array<AddObject?> = arrayOfNulls(size)
        }
    }
}