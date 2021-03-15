package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class DependentLocality(
    @SerializedName("DependentLocalityName")
    val dependentLocalityName: String? = null,
    @SerializedName("Premise")
    val premise: Premise? = null
)