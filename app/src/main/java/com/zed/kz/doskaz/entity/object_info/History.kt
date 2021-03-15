package com.zed.kz.doskaz.entity.object_info


import com.google.gson.annotations.SerializedName
import com.zed.kz.doskaz.entity.Data

data class History(
    @SerializedName("data")
    val datas: Data? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("name")
    val name: String? = null
)