package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class AddressDetails(
    @SerializedName("Country")
    val country: Country? = null
)