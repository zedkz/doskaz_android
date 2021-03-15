package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class Locality(
    @SerializedName("DependentLocality")
    val dependentLocality: DependentLocality? = null,
    @SerializedName("LocalityName")
    val localityName: String? = null
)