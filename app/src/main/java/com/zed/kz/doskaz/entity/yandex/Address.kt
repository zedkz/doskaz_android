package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("Components")
    val components: List<Component>? = null,
    @SerializedName("country_code")
    val countryCode: String? = null,
    @SerializedName("formatted")
    val formatted: String? = null
)