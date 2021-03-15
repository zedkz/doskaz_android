package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class GeocoderResponseMetaData(
    @SerializedName("found")
    val found: String? = null,
    @SerializedName("Point")
    val point: PointX? = null,
    @SerializedName("request")
    val request: String? = null,
    @SerializedName("results")
    val results: String? = null
)