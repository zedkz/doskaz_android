package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("bounds")
    val bounds: List<List<Double>>? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)