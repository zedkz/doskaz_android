package com.zed.kz.doskaz.entity

import com.google.gson.annotations.SerializedName

data class BlogCommentWrapper(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("items")
    val items: List<BlogComment>? = null
)