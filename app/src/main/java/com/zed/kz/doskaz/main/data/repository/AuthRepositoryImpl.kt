package com.zed.kz.doskaz.main.data.repository

import com.zed.kz.doskaz.entity.AuthCredential
import com.zed.kz.doskaz.entity.Feedback
import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.global.service.ServerService
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response

class AuthRepositoryImpl(
    private val serverService: ServerService
): AuthRepository{

    override fun authFirebase(authCredential: AuthCredential): Single<Response<User>> =
        serverService.authFirebase(authCredential)

    override fun sendFeedback(feedback: Feedback): Completable =
        serverService.sendFeedback(feedback)

    override fun authGoogle(user: User): Single<Response<User>> =
        serverService.authGoogle(user)

    override fun authFacebook(user: User): Single<Response<User>> =
        serverService.authFacebook(user)

    override fun authVk(user: User): Single<Response<User>> =
        serverService.authVk(user)

    override fun authMailru(user: User): Single<Response<User>> =
        serverService.authMailru(user)

}