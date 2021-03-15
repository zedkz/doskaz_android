package com.zed.kz.doskaz.global.utils

import com.zed.kz.doskaz.global.extension.errorMessage
import com.zed.kz.doskaz.global.system.ResourceManager


class ErrorHandler(private val resourceManager: ResourceManager) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit = {}) {
        messageListener(error.errorMessage(resourceManager))
    }
}