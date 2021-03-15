package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("AddressLine")
    val addressLine: String? = null,
    @SerializedName("AdministrativeArea")
    val administrativeArea: AdministrativeArea? = null,
    @SerializedName("CountryName")
    val countryName: String? = null,
    @SerializedName("CountryNameCode")
    val countryNameCode: String? = null
)