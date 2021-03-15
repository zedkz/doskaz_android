package com.zed.kz.doskaz.entity.form


import com.google.gson.annotations.SerializedName

data class AddObjectSubGroups(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("attributes")
    val attributes: List<AddObjectAttribute>? = null
)