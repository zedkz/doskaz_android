package com.zed.kz.doskaz.entity.form


import com.google.gson.annotations.SerializedName

data class AddObjectGroup(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("subGroups")
    val subGroups: List<AddObjectSubGroups>? = null
)