package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Feedback(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("text")
    val text: String? = null
)