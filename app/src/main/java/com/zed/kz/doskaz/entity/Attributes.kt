package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Attributes(
    @SerializedName("attributes")
    var attributes: Attribute? = Attribute(),
    @SerializedName("comment")
    var comment: String? = null
)