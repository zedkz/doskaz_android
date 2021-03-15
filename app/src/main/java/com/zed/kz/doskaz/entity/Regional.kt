package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Regional(
    @SerializedName("cityId")
    val cityId: Int? = null,
    @SerializedName("department")
    val department: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null
)