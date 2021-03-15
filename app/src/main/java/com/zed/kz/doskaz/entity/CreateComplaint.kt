package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class CreateComplaint(
    @SerializedName("authorityId")
    var authorityId: Int? = null,
    @SerializedName("complainant")
    var complainant: Complainant? = Complainant(),
    @SerializedName("content")
    var content: Content? = Content(),
    @SerializedName("objectId")
    var objectId: Int? = null,
    @SerializedName("rememberPersonalData")
    var rememberPersonalData: Boolean? = null
)