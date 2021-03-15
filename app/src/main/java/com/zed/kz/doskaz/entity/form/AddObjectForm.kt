package com.zed.kz.doskaz.entity.form

import com.google.gson.annotations.SerializedName

data class AddObjectForm(
    @SerializedName("small")
    val small: AddObjectSubForm? = null,
    @SerializedName("middle")
    val middle: AddObjectSubForm? = null,
    @SerializedName("full")
    val full: AddObjectSubForm? = null
)