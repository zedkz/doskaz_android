package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("complaints")
    val complaints: Int? = null,
    @SerializedName("objects")
    val objects: Int? = null
)