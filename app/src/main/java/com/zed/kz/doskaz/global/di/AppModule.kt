package com.zed.kz.doskaz.global.di

import com.zed.kz.doskaz.main.di.interactorAndRepositoryModule
import com.zed.kz.doskaz.main.di.mainModule

val appModule = listOf(networkModule, utilModule, interactorAndRepositoryModule, mainModule)