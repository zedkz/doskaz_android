package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class CreateObject(
    @SerializedName("first")
    var first: First? = null,
    @SerializedName("form")
    var form: String? = null,
    @SerializedName("entrance1")
    var entrance1: Attributes? = Attributes(),
    @SerializedName("movement")
    var movement: Attributes? = Attributes(),
    @SerializedName("navigation")
    var navigation: Attributes? = Attributes(),
    @SerializedName("parking")
    var parking: Attributes? = Attributes(),
    @SerializedName("service")
    var service: Attributes? = Attributes(),
    @SerializedName("serviceAccessibility")
    var serviceAccessibility: Attributes? = Attributes(),
    @SerializedName("toilet")
    var toilet: Attributes? = Attributes()
)