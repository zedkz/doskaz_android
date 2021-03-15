package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class MyObject(
    @SerializedName("complaintsCount")
    val complaintsCount: Int? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("overallScore")
    val overallScore: String? = null,
    @SerializedName("reviewsCount")
    val reviewsCount: Int? = null,
    @SerializedName("title")
    val title: String? = null
)