package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Level(
    @SerializedName("current")
    val current: Int? = null,
    @SerializedName("currentPoints")
    val currentPoints: Int? = null,
    @SerializedName("nextLevelThreshold")
    val nextLevelThreshold: Int? = null,
    @SerializedName("progressToNext")
    val progressToNext: Int? = null
)