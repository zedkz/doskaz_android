package com.zed.kz.doskaz.entity

import com.google.gson.annotations.SerializedName

data class Upload(
    @SerializedName("path")
    val path: String? = null
)