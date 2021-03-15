package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("categorySlug")
    val categorySlug: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("objectId")
    val objectId: Int? = null,
    @SerializedName("postId")
    val postId: Int? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null
)