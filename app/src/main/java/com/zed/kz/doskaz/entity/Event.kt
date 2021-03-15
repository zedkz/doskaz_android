package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("type")
    val type: String? = null
)