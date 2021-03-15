package com.zed.kz.doskaz.entity.yandex


import com.google.gson.annotations.SerializedName

data class GeoObjectCollection(
    @SerializedName("featureMember")
    val featureMember: List<FeatureMember>? = null,
    @SerializedName("metaDataProperty")
    val metaDataProperty: MetaDataPropertyX? = null
)