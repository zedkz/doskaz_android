package com.zed.kz.doskaz.global.functional

import android.content.Context
import com.zed.kz.doskaz.global.extension.networkInfo

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}