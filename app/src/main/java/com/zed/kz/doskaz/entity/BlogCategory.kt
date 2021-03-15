package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class BlogCategory(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("title")
    val title: String? = null,
    var isSelected: Boolean = false
)