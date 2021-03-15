package com.zed.kz.doskaz.entity.form


import com.google.gson.annotations.SerializedName

data class AddObjectSubForm(
    @SerializedName("parking")
    val parking: List<AddObjectGroup>? = null,
    @SerializedName("entrance")
    val entrance: List<AddObjectGroup>? = null,
    @SerializedName("movement")
    val movement: List<AddObjectGroup>? = null,
    @SerializedName("service")
    val service: List<AddObjectGroup>? = null,
    @SerializedName("toilet")
    val toilet: List<AddObjectGroup>? = null,
    @SerializedName("navigation")
    val navigation: List<AddObjectGroup>? = null,
    @SerializedName("serviceAccessibility")
    val serviceAccessibility: List<AddObjectGroup>? = null,
    @SerializedName("kidsAccessibility")
    val kidsAccessibility: List<AddObjectGroup>? = null
)