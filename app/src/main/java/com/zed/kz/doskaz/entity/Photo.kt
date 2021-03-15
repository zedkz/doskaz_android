package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("previewUrl")
    val previewUrl: String? = null,
    @SerializedName("viewUrl")
    val viewUrl: String? = null
)