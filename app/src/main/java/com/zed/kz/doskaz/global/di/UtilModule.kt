package com.zed.kz.doskaz.global.di


import com.zed.kz.doskaz.global.functional.NetworkHandler
import com.zed.kz.doskaz.global.system.AppSchedulers
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.system.SchedulersProvider
import com.zed.kz.doskaz.global.utils.ErrorHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single { AppSchedulers() as SchedulersProvider }
    single { ResourceManager(androidContext()) }
    single { ErrorHandler(get()) }
    single { NetworkHandler(androidContext()) }
}