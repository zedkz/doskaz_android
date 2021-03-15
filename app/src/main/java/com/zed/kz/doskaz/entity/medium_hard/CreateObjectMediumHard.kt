package com.zed.kz.doskaz.entity.medium_hard

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.zed.kz.doskaz.entity.First

data class CreateObjectMediumHard(
    @SerializedName("first")
    var first: First? = null,
    @SerializedName("form")
    var form: String? = null,
    @SerializedName("entrance1")
    var entrance1: JsonObject? = null,
    @SerializedName("movement")
    var movement: JsonObject? = null,
    @SerializedName("navigation")
    var navigation: JsonObject? = null,
    @SerializedName("parking")
    var parking: JsonObject? = null,
    @SerializedName("service")
    var service: JsonObject? = null,
    @SerializedName("serviceAccessibility")
    var serviceAccessibility: JsonObject? = null,
    @SerializedName("kidsAccessibility")
    var kidsAccessibility: JsonObject? = null,
    @SerializedName("toilet")
    var toilet: JsonObject? = null
){

    companion object{

        private var instance: CreateObjectMediumHard? = null

        fun newInstance(formType: String): CreateObjectMediumHard? {
            instance = CreateObjectMediumHard()
            instance?.form = formType
            return instance
        }

        fun getInstance(): CreateObjectMediumHard? = instance

    }

}