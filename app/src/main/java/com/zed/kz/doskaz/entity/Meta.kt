package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("keywords")
    val keywords: Any? = null,
    @SerializedName("ogDescription")
    val ogDescription: String? = null,
    @SerializedName("ogImage")
    val ogImage: String? = null,
    @SerializedName("ogTitle")
    val ogTitle: String? = null,
    @SerializedName("title")
    val title: String? = null
)