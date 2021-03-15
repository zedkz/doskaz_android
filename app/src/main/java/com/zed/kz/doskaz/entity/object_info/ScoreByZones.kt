package com.zed.kz.doskaz.entity.object_info


import com.google.gson.annotations.SerializedName

data class ScoreByZones(
    @SerializedName("entrance")
    val entrance: String? = null,
    @SerializedName("movement")
    val movement: String? = null,
    @SerializedName("navigation")
    val navigation: String? = null,
    @SerializedName("parking")
    val parking: String? = null,
    @SerializedName("service")
    val service: String? = null,
    @SerializedName("serviceAccessibility")
    val serviceAccessibility: String? = null,
    @SerializedName("toilet")
    val toilet: String? = null,
    @SerializedName("kidsAccessibility")
    val kidsAccessibility: String? = null
)