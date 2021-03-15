package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class SubOption(
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("label")
    val label: String? = null,
    var isSelected: Boolean = false
)