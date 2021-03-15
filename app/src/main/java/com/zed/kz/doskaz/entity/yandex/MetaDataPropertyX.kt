package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class MetaDataPropertyX(
    @SerializedName("GeocoderResponseMetaData")
    val geocoderResponseMetaData: GeocoderResponseMetaData? = null
)