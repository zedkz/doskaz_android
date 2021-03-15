package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Complaint(
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null
)