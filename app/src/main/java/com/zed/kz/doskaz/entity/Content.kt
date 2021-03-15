package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("building")
    var building: String? = null,
    @SerializedName("objectName")
    var objectName: String? = null,
    @SerializedName("office")
    var office: String? = null,
    @SerializedName("photos")
    var photos: MutableList<String>? = mutableListOf(),
    @SerializedName("street")
    var street: String? = null,
    @SerializedName("type")
    var type: String? = "complaint1",
    @SerializedName("videos")
    var videos: List<String>? = listOf(),
    @SerializedName("visitPurpose")
    var visitPurpose: String? = null,
    @SerializedName("visitedAt")
    var visitedAt: String? = null,
    @SerializedName("cityId")
    var cityId: Int? = null,
    @SerializedName("options")
    var options: MutableList<String>? = mutableListOf(),
    @SerializedName("threatToLife")
    var threatToLife: Boolean? = null,
    @SerializedName("comment")
    var comment: String? = null
)