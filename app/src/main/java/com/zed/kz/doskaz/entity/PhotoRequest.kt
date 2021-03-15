package com.zed.kz.doskaz.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PhotoRequest(
    @SerializedName("photos")
    val photos: MutableList<String> = mutableListOf()
)