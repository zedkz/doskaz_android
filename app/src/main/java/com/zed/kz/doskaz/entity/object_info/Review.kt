package com.zed.kz.doskaz.entity.object_info


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("text")
    val text: String? = null
)