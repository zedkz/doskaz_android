package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class DisabilityCategory(
    @SerializedName("categoryForAPI")
    val categoryForAPI: String? = null,
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("kazTitle")
    val kazTitle: String? = null,
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("title")
    val title: String? = null,
    var isSelected: Boolean = false
)