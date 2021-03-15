package com.zed.kz.doskaz.entity

import com.google.gson.annotations.SerializedName

data class BlogWrapper(
    @SerializedName("post")
    val post: Blog? = null,
    @SerializedName("similar")
    val similar: List<Blog>? = null
)