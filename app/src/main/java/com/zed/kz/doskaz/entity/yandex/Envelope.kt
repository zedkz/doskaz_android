package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class Envelope(
    @SerializedName("lowerCorner")
    val lowerCorner: String? = null,
    @SerializedName("upperCorner")
    val upperCorner: String? = null
)