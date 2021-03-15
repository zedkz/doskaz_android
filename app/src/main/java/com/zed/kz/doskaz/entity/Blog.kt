package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Blog(
    @SerializedName("annotation")
    val `annotation`: String? = null,
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("categorySlug")
    val categorySlug: String? = null,
    @SerializedName("categoryTitle")
    val categoryTitle: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("meta")
    val meta: Meta? = null,
    @SerializedName("previewImage")
    val previewImage: String? = null,
    @SerializedName("publishedAt")
    val publishedAt: String? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("title")
    val title: String? = null
)