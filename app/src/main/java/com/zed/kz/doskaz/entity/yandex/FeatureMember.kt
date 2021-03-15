package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class FeatureMember(
    @SerializedName("GeoObject")
    val geoObject: GeoObject? = null
)