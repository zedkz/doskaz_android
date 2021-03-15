package com.zed.kz.doskaz.global.system

import android.content.Context
import androidx.core.content.ContextCompat

class ResourceManager (private val context: Context) {

    fun getString(id: Int) = context.getString(id)

    fun getColor(id: Int) = ContextCompat.getColor(context, id)
}