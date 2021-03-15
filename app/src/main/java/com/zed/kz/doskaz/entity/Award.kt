package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Award(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null
)