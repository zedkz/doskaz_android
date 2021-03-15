package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("GeoObjectCollection")
    val geoObjectCollection: GeoObjectCollection? = null
)