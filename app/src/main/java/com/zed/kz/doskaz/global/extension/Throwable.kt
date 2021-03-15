package com.zed.kz.doskaz.global.extension

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.system.ResourceManager
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException


fun Throwable.getObjectErrorMessage(): String{
    if (this is HttpException){
        val body = this.response()?.errorBody()
        val jsonObject = JSONObject(body?.string().toString())
        return jsonObject.getString("message")
    }
    return ""
}

fun Throwable.getValidationErrorMessage(): String{
    if (this is HttpException){
        val body = this.response()?.errorBody()
        val jsonObject = JSONObject(body?.string().toString())
        val jsonArrays = jsonObject.getJSONArray("errors")
        if (jsonArrays.length() > 0)
            return jsonArrays.getJSONObject(0).getString("message")
        return ""
    }
    return ""
}

fun Throwable.getObjectErrorMessage(resourceManager: ResourceManager): String{
    if (this is HttpException){
        val body = this.response()?.errorBody()
        val jsonObject = JSONObject(body?.string().toString())
        return try {
            val errors = jsonObject.getJSONArray("errors")
            if (errors.length() > 0) {
                errors.optJSONObject(0).let {
                    val msg = "\"${validateErrorProperty(resourceManager, it.getString("property"))}\" ${it.getString("message")}"
                    msg
                }
            }else
                ""
        }catch (e: JSONException){
            e.printStackTrace()
            ""
        }
    }
    return ""
}

private fun validateErrorProperty(resourceManager: ResourceManager, property: String): String =
    when(property){
        "first.name" -> resourceManager.getString(R.string.object_name)
        "first.description" -> resourceManager.getString(R.string.object_desc)
        "first.otherNames" -> resourceManager.getString(R.string.object_other_name)
        "first.address" -> resourceManager.getString(R.string.object_address)
        "first.categoryId" -> resourceManager.getString(R.string.object_sub_category)
        "first.point" -> resourceManager.getString(R.string.object_map)
        "parking" -> resourceManager.getString(R.string.object_parking)
        "entrance1" -> resourceManager.getString(R.string.object_in_group)
        "movement" -> resourceManager.getString(R.string.object_rout)
        "service" -> resourceManager.getString(R.string.object_service_zone)
        "toilet" -> resourceManager.getString(R.string.object_toilet)
        "navigation" -> resourceManager.getString(R.string.object_navigation)
        "serviceAccessibility" -> resourceManager.getString(R.string.om_serviceAccessibility)
        else -> ""

    }

class RetrofitError(
    @Expose
    @SerializedName("message")
    val message: String = ""
)