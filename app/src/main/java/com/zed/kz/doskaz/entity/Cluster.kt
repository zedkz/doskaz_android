package com.zed.kz.doskaz.entity


import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem

data class Cluster(
    @SerializedName("bbox")
    val bbox: List<List<Double>>? = null,
    @SerializedName("coordinates")
    val coordinates: List<Double>? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("itemsCount")
    val itemsCount: Int? = null
): ClusterItem{

    override fun getPosition(): LatLng =
        LatLng(coordinates?.get(0) ?: 0.0, coordinates?.get(1) ?: 0.0)

    override fun getSnippet(): String? = "+${itemsCount}"

    override fun getTitle(): String? = "+${itemsCount}"

}