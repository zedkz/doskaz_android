package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class MetaDataProperty(
    @SerializedName("GeocoderMetaData")
    val geocoderMetaData: GeocoderMetaData? = null
)