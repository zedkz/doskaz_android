package com.zed.kz.doskaz.global.presentation

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class GlobalMenuController {
    private val stateRelay = PublishRelay.create<Boolean>()

    val state: Observable<Boolean> = stateRelay
    fun open() = stateRelay.accept(true)
    fun close() = stateRelay.accept(false)
}