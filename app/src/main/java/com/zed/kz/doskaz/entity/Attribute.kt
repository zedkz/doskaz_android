package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName
import com.zed.kz.doskaz.global.utils.AppConstants

data class Attribute(
    @SerializedName("attribute1")
    var attribute1: String? = null,
    @SerializedName("attribute2")
    var attribute2: String? = null,
    @SerializedName("attribute6")
    var attribute6: String? = null,
    @SerializedName("attribute7")
    var attribute7: String? = null,
    @SerializedName("attribute30")
    var attribute30: String? = null,
    @SerializedName("attribute31")
    var attribute31: String? = null,
    @SerializedName("attribute1000")
    var attribute1000: String? = null,
    @SerializedName("attribute1001")
    var attribute1001: String? = null,
    @SerializedName("attribute1002")
    var attribute1002: String? = null
)