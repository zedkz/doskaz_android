package com.zed.kz.doskaz.entity

import com.google.gson.annotations.SerializedName

data class OneOf(
    @SerializedName("threatToLife")
    var threatToLife: Boolean? = null,

    @SerializedName("comment")
    var comment: String? = null,

    @SerializedName("options")
    var options: MutableList<String>? = mutableListOf()
)