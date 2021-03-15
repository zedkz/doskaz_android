package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Option(
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("options")
    val options: List<SubOption>? = null,
    var isSelected: Boolean = false,
    var count: Int? = null
)