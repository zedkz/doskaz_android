package com.zed.kz.doskaz.entity.object_info

import com.google.gson.annotations.SerializedName

data class ObjectInfoAttributes(
    @SerializedName("form")
    var form: String? = null,
    @SerializedName("zones")
    var zones: Zones? = null
)