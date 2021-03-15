package com.zed.kz.doskaz.main.data.repository

import com.zed.kz.doskaz.entity.AuthCredential
import com.zed.kz.doskaz.entity.Feedback
import com.zed.kz.doskaz.entity.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response

interface AuthRepository {

    fun authFirebase(authCredential: AuthCredential): Single<Response<User>>

    fun sendFeedback(feedback: Feedback): Completable

    fun authGoogle(user: User): Single<Response<User>>

    fun authFacebook(user: User): Single<Response<User>>

    fun authVk(user: User): Single<Response<User>>

    fun authMailru(user: User): Single<Response<User>>

}