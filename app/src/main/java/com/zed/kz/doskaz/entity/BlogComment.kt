package com.zed.kz.doskaz.entity


import com.google.gson.annotations.SerializedName

data class BlogComment(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("parentId")
    val parentId: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("userAvatar")
    val userAvatar: String? = null,
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("userName")
    val userName: String? = null,
    @SerializedName("replies")
    val replies: List<BlogComment>? = null,
    var parentName: String? = null
)