package com.zed.kz.doskaz.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar")
    var avatar: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("middleName")
    var middleName: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("token")
    var token: String? = null,
    @SerializedName("currentTask")
    var currentTask: Task? = null,
    @SerializedName("level")
    var level: Level? = null,
    @SerializedName("stats")
    var stats: Stat? = null,
    @SerializedName("phoneChangeToken")
    var phoneChangeToken: String? = null,
    @Expose(deserialize = false, serialize = false)
    var imagePath: String? = null,
    @Expose(deserialize = false, serialize = false)
    var newPhone: String? = null
)