package com.zed.kz.doskaz.main.data.interactor

import com.zed.kz.doskaz.entity.AuthCredential
import com.zed.kz.doskaz.entity.Feedback
import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.global.system.SchedulersProvider
import com.zed.kz.doskaz.main.data.repository.AuthRepository
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response

class AuthInteractor(
    private val schedulersProvider: SchedulersProvider,
    private val authRepository: AuthRepository
){

    fun authFirebase(authCredential: AuthCredential): Single<Response<User>> =
        authRepository.authFirebase(authCredential)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun sendFeedback(feedback: Feedback): Completable =
        authRepository.sendFeedback(feedback)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun authGoogle(user: User): Single<Response<User>> =
        authRepository.authGoogle(user)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun authFacebook(user: User): Single<Response<User>> =
        authRepository.authFacebook(user)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun authVk(user: User): Single<Response<User>> =
        authRepository.authVk(user)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun authMailru(user: User): Single<Response<User>> =
        authRepository.authMailru(user)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
}