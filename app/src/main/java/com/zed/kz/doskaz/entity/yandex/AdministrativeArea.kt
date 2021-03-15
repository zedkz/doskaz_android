package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class AdministrativeArea(
    @SerializedName("AdministrativeAreaName")
    val administrativeAreaName: String? = null,
    @SerializedName("Locality")
    val locality: Locality? = null
)