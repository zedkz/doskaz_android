package com.zed.kz.doskaz.entity.object_info


import com.google.gson.annotations.SerializedName
import com.zed.kz.doskaz.entity.*

data class ObjectItem(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("coordinates")
    val coordinates: List<Double>? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("overallScore")
    val overallScore: String? = null,
    @SerializedName("score")
    val score: String? = null,
    @SerializedName("subCategory")
    val subCategory: String? = null,
    @SerializedName("subCategoryId")
    val subCategoryId: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("scoreByZones")
    val scoreByZones: ScoreByZones? = null,
    @SerializedName("verificationStatus")
    val verificationStatus: String? = null,
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("photos")
    val photos: List<Photo>? = null,
    @SerializedName("reviews")
    val reviews: List<Review>? = null,
    @SerializedName("videos")
    val videos: List<Video>? = null,
    @SerializedName("history")
    val history: List<History>? = null,
    @SerializedName("attributes")
    val attributes: ObjectInfoAttributes? = null
)