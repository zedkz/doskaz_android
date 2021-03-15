package com.zed.kz.doskaz.entity

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("pages")
    val pages: Int? = null,
    @SerializedName("items")
    val items: JsonArray? = null
)