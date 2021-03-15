package com.zed.kz.doskaz.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("subCategories")
    val subCategories: List<Category>? = null,
    @Expose(deserialize = false, serialize = false)
    var isExpose: Boolean = false,
    @Expose(deserialize = false, serialize = false)
    var isSelected: Boolean = false,
    @Expose(deserialize = false, serialize = false)
    var type: String = ""
)