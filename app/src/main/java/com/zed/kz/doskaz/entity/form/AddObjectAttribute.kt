package com.zed.kz.doskaz.entity.form


import com.google.gson.annotations.SerializedName

data class AddObjectAttribute(
    @SerializedName("key")
    val key: Int? = null,
    @SerializedName("subTitle")
    val subTitle: String? = null,
    @SerializedName("title")
    val title: String? = null
)