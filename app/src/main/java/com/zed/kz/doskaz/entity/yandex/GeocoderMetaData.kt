package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class GeocoderMetaData(
    @SerializedName("Address")
    val address: Address? = null,
    @SerializedName("AddressDetails")
    val addressDetails: AddressDetails? = null,
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("precision")
    val precision: String? = null,
    @SerializedName("text")
    val text: String? = null
)