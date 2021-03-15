package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class First(
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("categoryId")
    var categoryId: Int? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("otherNames")
    var otherNames: String? = null,
    @SerializedName("photos")
    var photos: MutableList<String>? = mutableListOf(),
    @SerializedName("point")
    var point: MutableList<Double>? = mutableListOf(),
    @SerializedName("videos")
    var videos: List<String>? = mutableListOf()
)