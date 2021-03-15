package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Authority(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)