package com.zed.kz.doskaz.entity.object_info

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Zones(
    @SerializedName("entrance1")
    var entrance1: JsonObject? = null,
    @SerializedName("movement")
    var movement: JsonObject? = null,
    @SerializedName("navigation")
    var navigation: JsonObject? = null,
    @SerializedName("parking")
    var parking: JsonObject? = null,
    @SerializedName("service")
    var service: JsonObject? = null,
    @SerializedName("serviceAccessibility")
    var serviceAccessibility: JsonObject? = null,
    @SerializedName("toilet")
    var toilet: JsonObject? = null,
    @SerializedName("kidsAccessibility")
    var kidsAccessibility: JsonObject? = null
)