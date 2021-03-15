package com.zed.kz.doskaz.entity.object_info


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("videoId")
    val videoId: Any? = null
)