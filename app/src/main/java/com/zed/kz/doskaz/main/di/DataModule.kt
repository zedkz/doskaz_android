package com.zed.kz.doskaz.main.di

import android.view.animation.BounceInterpolator
import com.zed.kz.doskaz.main.data.interactor.*
import com.zed.kz.doskaz.main.data.repository.*
import org.koin.dsl.module

val interactorAndRepositoryModule = module {

    single<AuthRepository>{ AuthRepositoryImpl(get()) }
    single { AuthInteractor(get(), get()) }

    single<UserRepository>{ UserRepositoryImpl(get()) }
    single { UserInteractor(get(), get()) }

    single<ListRepository>{ ListRepositoryImpl(get(), get()) }
    single { ListInteractor(get(), get(), get()) }

    single<ObjectRepository>{ ObjectRepositoryImpl(get()) }
    single { ObjectInteractor(get(), get()) }

    single<BlogRepository>{ BlogRepositoryImpl(get()) }
    single { BlogInteractor(get(), get()) }

}