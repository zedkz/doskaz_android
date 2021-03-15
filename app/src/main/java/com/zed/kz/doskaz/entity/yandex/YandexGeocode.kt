package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class YandexGeocode(
    @SerializedName("response")
    val response: Response? = null
)