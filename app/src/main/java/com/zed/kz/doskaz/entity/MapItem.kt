package com.zed.kz.doskaz.entity

import com.google.gson.annotations.SerializedName
import com.zed.kz.doskaz.entity.object_info.ObjectItem

data class MapItem(
    @SerializedName("points")
    val points: List<ObjectItem>? = null,
    @SerializedName("clusters")
    val clusters: List<Cluster>? = null
)