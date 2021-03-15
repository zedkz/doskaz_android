package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class BoundedBy(
    @SerializedName("Envelope")
    val envelope: Envelope? = null
)