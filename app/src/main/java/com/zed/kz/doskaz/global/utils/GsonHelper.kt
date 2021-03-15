package com.zed.kz.doskaz.global.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object GsonHelper {

    internal fun <T> getModelArray(jsonArray: JsonArray?, dataClass: Class<T>): List<T> {
        return Gson().fromJson(jsonArray.toString(), getType(MutableList::class.java, dataClass))
    }

    internal fun <T> getModelArray(arrayStr: String?, dataClass: Class<T>): List<T> {
        return Gson().fromJson(arrayStr, getType(MutableList::class.java, dataClass))
    }

    fun getType(cll: Class<*>, param: Class<*>): Type{
        return object : ParameterizedType {
            override fun getRawType(): Type {
                return cll
            }

            override fun getOwnerType(): Type? {
                return null
            }

            override fun getActualTypeArguments(): Array<Type> {
                return Array(1){param}
            }

        }
    }

}