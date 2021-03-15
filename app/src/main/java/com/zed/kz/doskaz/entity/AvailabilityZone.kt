package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class AvailabilityZone(
    @SerializedName("hearing")
    val hearing: String? = null,
    @SerializedName("intellectual")
    val intellectual: String? = null,
    @SerializedName("limb")
    val limb: String? = null,
    @SerializedName("movement")
    val movement: String? = null,
    @SerializedName("vision")
    val vision: String? = null
)