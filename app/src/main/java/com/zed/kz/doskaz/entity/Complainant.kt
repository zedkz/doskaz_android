package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Complainant(
    @SerializedName("apartment")
    var apartment: String? = null,
    @SerializedName("building")
    var building: String? = null,
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("iin")
    var iin: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("middleName")
    var middleName: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("street")
    var street: String? = null,
    @SerializedName("cityId")
    var cityId: Int? = null
)