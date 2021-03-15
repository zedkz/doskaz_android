package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class GeoObject(
    @SerializedName("boundedBy")
    val boundedBy: BoundedBy? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("metaDataProperty")
    val metaDataProperty: MetaDataProperty? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("Point")
    val point: Point? = null
)