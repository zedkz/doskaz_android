package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("pointsReward")
    val pointsReward: Int? = null,
    @SerializedName("progress")
    val progress: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("completedAt")
    val completedAt: String? = null,
    @SerializedName("points")
    val points: Int? = null
)