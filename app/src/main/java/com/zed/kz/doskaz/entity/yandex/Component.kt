package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class Component(
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("name")
    val name: String? = null
)